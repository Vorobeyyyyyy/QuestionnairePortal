import {paginationComponent} from "/component/pagination.js";

const fieldsApp = {
    data() {
        return {
            fields: [],
            page: {
                number: 0,
                totalPages: 0,
                size: 5,
                totalElements: 0,
                first: false,
                last: false
            },
            addForm: {
                id: null,
                label: "",
                type: "",
                options: "",
                required: false,
                active: true
            },
            toDeleteField: {
                id: 0,
                label: null
            },
            deleteModal: {
                done: false,
            }
        }
    },
    mounted() {
        this.updateFields(0);
    },
    computed: {
        haveOptions() {
            return (["Checkbox", "Radio Button", "Combobox"].includes(this.addForm.type));
        }
    },
    methods: {
        pageClicked(page) {
            this.updateFields(page);
        },
        pageSizeChanged(size) {
            let newPageCount = Math.ceil(this.page.totalElements / size);
            if (newPageCount === Infinity) {
                newPageCount = 1;
            }
            this.updateFields(Math.min(newPageCount - 1, this.page.number), size);
        },
        newField() {
            this.addForm.id = null;
            this.addForm.label = "";
            this.addForm.type = "";
            this.addForm.active = true;
            this.addForm.options = "";
            this.addForm.required = false;
        },
        addFieldSubmit(event) {
            const data = {};
            Object.assign(data, this.addForm);
            data.options = data.options.split('\n');
            axios.post("/api/addField", data)
                .then(response => {
                    document.querySelector("#closeAddFieldModalButton").click();
                    this.updateFields();
                })
        },
        editField(field) {
            this.addForm.id = field.id;
            this.addForm.label = field.label;
            this.addForm.type = field.type;
            this.addForm.active = field.active;
            this.addForm.options = field.options.join('\n');
            this.addForm.required = field.required;
        },
        updateFields(page = this.page.number, size = this.page.size) {
            axios.get("/api/fields?" + new URLSearchParams({
                page: page,
                size: size
            }))
                .then(response => response.data)
                .then(data => {
                    this.fields = data.content;
                    this.page = data;
                });
        },
        deleteField() {
            axios.post("/api/deleteField", {id: this.toDeleteField.id})
                .then(response => {
                    console.log(response)
                    this.updateFields(this.page);
                    this.deleteModal.done = true;
                    setTimeout(() => {
                            document.querySelector("#closeDeleteModalButton").click();
                            setTimeout(() => this.deleteModal.done = false, 100);
                        }, 500
                    )
                })
        }
    }
}
let app = Vue.createApp(fieldsApp);
app.component('pagination', paginationComponent);
app.mount("#fields");