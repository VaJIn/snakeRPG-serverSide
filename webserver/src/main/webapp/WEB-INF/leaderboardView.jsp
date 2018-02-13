<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="table-responsive">
    <table class="table">

        <thead class="thead-dark">
        <th scope="col" class="col-md-6">
            Player
        </th>
        <th class="col-md-2 text-center" scope="col">
            Score
        </th>
        <th class="col-md-2" scope="col">
            Date
        </th>
        <th class="col-md-2"></th>
        </thead>

        <c:forEach items="${leaderboard_playerlist}" var="player">
            <tr class="border-top">
                <td>
                    <a href="/profile/${player.id}" class="">${player.name}</a>
                </td>
                <td class="text-center">
                        ${player.bestScore}
                </td>
                <td>
                    <fmt:formatNumber value="${player.bestScoreDate.dayOfMonth}"
                                      minIntegerDigits="2"/>/<fmt:formatNumber
                        value="${player.bestScoreDate.monthValue}" minIntegerDigits="2"/>/<fmt:formatNumber
                        value="${player.bestScoreDate.year}" minIntegerDigits="4" groupingUsed="FALSE"/>
                </td>
                <td>
                    <button class="leaderboard-player btn btn-outline-dark w-100" type="button" id="detail-${player.id}"
                            data-id="${player.id}" data-toggle="collapse" data-target="#player-${player.id}-collapse"
                            aria-expanded="true" aria-controls="player-${player.id}-collapse">
                        See details
                    </button>

                </td>
            </tr>
            <tr id="player-${player.id}-collapse" class="collapse" aria-labelledby="${player.id}">
                <td colspan="4">
                    <div class="card">
                        <div class="card-body">
                            Retrieving....
                        </div>
                    </div>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<script type="text/javascript" src="<c:url value="/inc/js/leaderboard.js"/>"></script>