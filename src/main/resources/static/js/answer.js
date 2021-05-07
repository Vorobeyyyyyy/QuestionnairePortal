let form = document.querySelector("#add_answer_form");
let savedForm = form.innerHTML;

function resetForm() {
    form.innerHTML = savedForm;
}