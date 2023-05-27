const deleteButton = document.getElementById("delete-btn");
const modifyButton = document.getElementById("modify-btn");
const createButton = document.getElementById("create-btn");

if (createButton) {
    createButton.addEventListener("click", _ => {
        fetch("/api/articles", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                title: document.getElementById("title").value,
                content: document.getElementById("content").value
            })
        }).then(() => {
            alert("등록 완료 되었습니다.");
            location.replace("/articles");
        }).catch((e) => {
            alert("에러가 발생했습니다." + e);
            location.replace("/articles");
        })
    })
}

if (deleteButton) {
    deleteButton.addEventListener("click", async (_) => {
        let id = document.getElementById("article-id").value;
        try {
            await fetch(`/api/articles/${id}`, {
                method: `DELETE`
            })
            alert("article has been created");
            location.replace('/articles/#');
        } catch (e) {
            alert("Something wrong..." + e);
        }
    })
}

if (modifyButton) {
    modifyButton.addEventListener("click", () => {
        let params = new URLSearchParams(location.search);
        let id = params.get('id');

        fetch(`/api/articles/${id}`, {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                title: document.getElementById("title").value,
                content: document.getElementById("content").value
            })
        }).then(() => {
            location.replace(`/articles/${id}`);
        })
    })
}