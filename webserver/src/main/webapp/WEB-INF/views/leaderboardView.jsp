<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:useBean id="listGameParticipation" scope="request" type="java.util.Collection"/>

<div class="table-responsive">
    <table class="table">

        <thead class="thead-dark">
        <th scope="col" class="col-md-1"></th>
        <th scope="col" class="col-md-5">
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

        <c:forEach items="${listGameParticipation}" var="gp" varStatus="status">
            <tr class="border-top">
                <td>
                        ${status.index + 1}
                </td>
                <td>
                    <a href="/player/${gp.snake.user.id}" class="">${gp.snake.user.alias}</a> ( ${gp.snake.name} )
                </td>
                <td class="text-center">
                        ${gp.score}
                </td>
                <td>
                    <c:out value="${gp.game.startTime}"/>
                </td>
                <td>
                    <button class="leaderboard-player btn btn-outline-dark w-100" type="button"
                            id="detail-${gp.idSnake}-${gp.idGame}-collapse"
                            data-game-id="${gp.game.id}" data-snake-id="${gp.snake.id}" data-toggle="collapse"
                            data-target="#score-${gp.idSnake}-${gp.idGame}-collapse"
                            aria-expanded="true" aria-controls="score-${gp.idSnake}#${gp.idGame}-collapse">
                        See details
                    </button>
                </td>
            </tr>
            <tr id="score-${gp.idSnake}-${gp.idGame}-collapse" class="collapse"
                aria-labelledby="detail-${gp.idSnake}-${gp.idGame}-collapse">
                <td colspan="4">
                    <div class="card">
                        <div class="card-body" id="score-${gp.idSnake}-${gp.idGame}-content">
                            Retrieving....
                        </div>
                    </div>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<script type="text/javascript" src="<c:url value="/inc/js/leaderboard.js"/>"></script>