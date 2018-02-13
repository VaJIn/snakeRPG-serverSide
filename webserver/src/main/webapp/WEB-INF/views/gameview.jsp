<%--
  Created by IntelliJ IDEA.
  User: pviolette
  Date: 03/02/18
  Time: 15:20
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div class="row">
    <!-- List group -->
    <div class="col-4 list-group" id="myList" role="tablist">
        <a class="list-group-item list-group-item-action active" data-toggle="list" href="#game${game.id}-sb"
           role="tab">Scoreboard</a>
        <a class="list-group-item list-group-item-action" data-toggle="list" href="#game${game.id}-info"
           role="tab">Info</a>
    </div>

    <!-- Tab panes -->
    <div class="col-8 tab-content">
        <div class="tab-pane active" id="game${game.id}-sb" role="tabpanel">
            <table class="table table-bordered">
                <thead class="thead-light">
                <th scope="col" style="max-width: 99%; font-size: 120%">Player</th>
                <th scope="col" style="font-size: 120%">Score</th>
                <th scope="col" style="font-size: 120%">Kill</th>
                <th scope="col" style="font-size: 120%">Death</th>
                </thead>
                <c:forEach var="i" begin="1" end="5" step="1">
                    <tr>
                        <td class="border p-1">
                            <a href="<c:url value="/player/${i}" />">Dummy_${i}</a>
                        </td>
                        <td class="border text-right p-1">
                            6969
                        </td>
                        <td class="border text-right p-1">
                            5
                        </td>
                        <td class="border text-right p-1">
                            3
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="tab-pane" id="game${game.id}-info" role="tabpanel">
            <table class="table table-bordered">
                <tr>
                    <th scope="row">Gamemode</th>
                    <td>${game.gameMode}</td>
                </tr>
                <tr>
                    <th scope="row">Start time</th>
                    <td>${game.startTime}</td>
                </tr>
                <tr>
                    <th scope="row">End time</th>
                    <td>${game.endTime}</td>
                </tr>
            </table>
        </div>
    </div>
</div>