var leaderboard_cached = new Set();

$(".leaderboard-player").click(function () {

    var elt = $(this);

    var id = elt.attr("data-id");

    if (elt.hasClass("open")) {
        elt.removeClass("open");
        elt.html("See details");
    } else {
        elt.addClass("open");
        elt.html("Hide details")
    }

    console.log(leaderboard_cached.has(id));
    if (!leaderboard_cached.has(id)) {
        console.log("Retrieving game info view for #" + id + " from server");
        $.ajax({
                url: "/game/" + id,
                method: "POST",
                success: function (data) {
                    console.log(data);
                    $("#player-" + id + "-collapse td").html(data);
                    leaderboard_cached.add(id);
                }
            }
        );
    }
    else {
        console.log("Game info already in cache")
    }
});