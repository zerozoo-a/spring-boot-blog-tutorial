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
            const a = await fetch(`/api/articles/${id}`, {
                method: `DELETE`
            })
            console.log('a', a);
            alert("SUCCESS! 204");
            location.replace('/articles/#');
        } catch (e) {
            alert("Something wrong..." + e);
        }
    })
}

if (modifyButton) {
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
        alert("modified")
        location.replace(`/articles/${id}`);
    })
}