var cur = 3;
$(window).scroll(function () {
    if ($(document).height() - $(this).height() == $(this).scrollTop()) {
            $.ajax({
                url: "/getDashboardByRank?page=0",
                method: "GET",
            }).done((data) => {
                console.log(data)
                console.log(cur)
                more(".col-md-7", data['articles']);
            });
    }
});
const  renderArticlesTo2 = (elem, articles) => {
    for (let i = 0; i < 3; i++) {
                $(elem).append(`<br>
            <div id="card" class="card"> <img class="card-img-top" src="https://static.pingendo.com/cover-moon.svg" alt="Card image cap">
                <div class="card-body">
                    <h4 class="card-title">
                    ${articles[i].title}
</h4>
                    <p class="card-content"></p>
                    <a href="article?id=${articles[i].articleId}
                    " class="btn btn-primary">Read More...</a>
                </div>
            </div>`);
            }
};
const  more = (elem, articles) => {
    for (let i = 0; i < 3; i++) {
        $(elem).append(`<br>
            <div id="card" class="card"> <img class="card-img-top" src="https://static.pingendo.com/cover-moon.svg" alt="Card image cap">
                <div class="card-body">
                    <h4 class="card-title">
                    ${articles[i+cur].title}
</h4>
                    <p class="card-content"></p>
                    <a href="article?id=${articles[i+cur].articleId}
                    " class="btn btn-primary">Read More...</a>
                </div>
            </div>`);
    }
    cur +=3;
    console.log(cur);

};
$(() => {
    $.ajax({
        url: "/getDashboardByRank?page=0",
        method: "GET",
    }).done((data) => {
        renderArticlesTo2(".col-md-7", data['articles']);
    });
});
