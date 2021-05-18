import {paginationComponent} from "/component/pagination.js";

const fieldsApp = {
    data() {
        return {
            fields: [],
            answers: [],
            page: {
                number: 0,
                totalPages: 0,
                size: 5,
                totalElements: 0,
                first: false,
                last: false
            }
        }
    },
    mounted() {
        this.updateFields();
        this.updateAnswers(this.page.number);
        this.connectWebSocket();
    },
    methods: {
        pageClicked(page) {
            this.updateAnswers(page);
        },
        pageSizeChanged(size) {
            let newPageCount = Math.ceil(this.page.totalElements / size);
            if (newPageCount === Infinity) {
                newPageCount = 1;
            }
            this.updateAnswers(Math.min(newPageCount - 1, this.page.number), size);
        },
        updateFields() {
            axios.get("/api/fields?" + new URLSearchParams({
                page: '0',
                size: '0'
            }))
                .then(response => response.data)
                .then(data => {
                    this.fields = data.content;
                });
        },
        updateAnswers(page, pageSize = this.page.size) {
            axios.get("/api/answers?" + new URLSearchParams({
                page: page,
                size: pageSize
            }))
                .then(response => response.data)
                .then(data => {
                    this.answers = data.content;
                    this.page = data;
                });

        },
        fieldAnswer(answer, field) {
            let result = answer.fieldAnswers.find(e => e.fieldId === field.id);
            if (result != null && result.options.length > 0 && result.options[0].length > 0) {
                result = result.options.join(", ");
            } else {
                result = "N/A"
            }
            return result;
        },
        connectWebSocket() {
            let connection = new SockJS("/ws");
            let stompClient = Stomp.over(connection);
            stompClient.debug = null;
            stompClient.connect({}, () => {
                stompClient.subscribe("/answers", (event) => {
                    if (event.body === "update") {
                        this.updateAnswers(this.page.number);
                    }
                });
            });
        }
    }
}


let app = Vue.createApp(fieldsApp);
app.component('pagination', paginationComponent);
app.mount("#fields");