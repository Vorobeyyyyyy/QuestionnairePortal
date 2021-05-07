export let paginationComponent = {
    props: ['page'],
    emits: ['onPageChange', 'onPageSizeChange'],
    computed: {
        currentShowing() {
            return (this.page.number * this.page.size + 1) + '-'
                + Math.min((this.page.number + 1) * this.page.size, this.page.totalElements);
        },
        pageSize() {
            return this.input.pageSize !== 'All' ? this.input.pageSize : 0;
        }
    },
    mounted() {
        this.input.selectedPage = this.page.number;
        this.input.pageSize = this.page.size;
    },
    data() {
        return {
            input: {
                selectedPage: 0,
                pageSize: 10,
            }
        }
    },
    methods: {
        changeSelectedPage(page) {
            this.input.selectedPage = page;
            this.$emit('onPageChange', page);
        }
    },
    template: ' <div class="d-flex m-2 mb-3 flex-row justify-content-between align-items-center">\n' +
        '                <div class="w-20">\n' +
        '                    {{currentShowing}} of {{page.totalElements}}\n' +
        '                </div>\n' +
        '                <nav>\n' +
        '                    <ul class="pagination mb-0">\n' +
        '                        <li :class="{\'disabled\' : page.first}" class="page-item">\n' +
        '                            <button @click="changeSelectedPage(this.input.selectedPage - 1)" class="page-link text-center h-100">\n' +
        '                                <div class="d-flex align-items-center h-100 bi-arrow-left"></div>\n' +
        '                            </button>\n' +
        '                        </li>\n' +
        '                        <li v-for="i in page.totalPages" :class="{\'active\' : i === input.selectedPage + 1}"\n' +
        '                            class="page-item">\n' +
        '                            <button @click="changeSelectedPage(i - 1)" class="page-link">{{i}}</button>\n' +
        '                        </li>\n' +
        '                        <li :class="{\'disabled\' : page.last}" class="page-item">\n' +
        '                            <button @click="changeSelectedPage(this.input.selectedPage + 1)" class="page-link text-center h-100">\n' +
        '                                <div class="d-flex align-items-center h-100 bi-arrow-right"></div>\n' +
        '                            </button>\n' +
        '                        </li>\n' +
        '                    </ul>\n' +
        '                </nav>\n' +
        '                <div class="w-20 d-flex flex-row justify-content-end"> \n' +
        '                    <div>\n' +
        '                        <label for="pageSizeSelect" class="d-none">Pagination Size</label>\n' +
        '                        <select @change="$emit(' + "'onPageSizeChange'" +', pageSize)" v-model="input.pageSize" class="form-select"\n' +
        '                                id="pageSizeSelect">\n' +
        '                            <option>All</option>\n' +
        '                            <option>1</option>\n' +
        '                            <option>5</option>\n' +
        '                            <option>10</option>\n' +
        '                            <option>25</option>\n' +
        '                            <option>50</option>\n' +
        '                        </select>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '   </div>'

}