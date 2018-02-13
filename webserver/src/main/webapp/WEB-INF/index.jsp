<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html >
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Snake RPG</title>

    <%-- Stylesheet : contains custom bootstrap + custom style element --%>
    <link rel="stylesheet"
          href="<c:url value="/inc/css/style.css" /> "
    />
</head>
<body>

<%-- Jquery --%>
<script src="<c:url value="/inc/js/jquery.min.js"/>" type="text/javascript"></script>


<%@ include file="navbar.jsp" %>

<div id="main" class="container-fluid m-2">
    <jsp:include page="/leaderboard/today">
        <jsp:param name="max_entry" value="50"/>
    </jsp:include>
    <div>
        <p class="text-justify"> Mox dicta finierat, multitudo omnis ad, quae imperator voluit, promptior laudato
            consilio consensit in pacem
            ea ratione maxime percita, quod norat expeditionibus crebris fortunam eius in malis tantum civilibus
            vigilasse, cum autem bella moverentur externa, accidisse plerumque luctuosa, icto post haec foedere gentium
            ritu perfectaque sollemnitate imperator Mediolanum ad hiberna discessit.
        </p>
        <p class="text-justify"> Eodem tempore Serenianus ex duce, cuius ignavia populatam in Phoenice Celsen ante
            rettulimus, pulsatae
            maiestatis imperii reus iure postulatus ac lege, incertum qua potuit suffragatione absolvi, aperte convictus
            familiarem suum cum pileo, quo caput operiebat, incantato vetitis artibus ad templum misisse fatidicum,
            quaeritatum expresse an ei firmum portenderetur imperium, ut cupiebat, et cunctum.
        </p>
        <p class="text-justify"> Vide, quantum, inquam, fallare, Torquate. oratio me istius philosophi non offendit; nam
            et complectitur
            verbis, quod vult, et dicit plane, quod intellegam; et tamen ego a philosopho, si afferat eloquentiam, non
            asperner, si non habeat, non admodum flagitem. re mihi non aeque satisfacit, et quidem locis pluribus. sed
            quot homines, tot sententiae; falli igitur possumus.
        </p>
        <p class="text-justify"> Mensarum enim voragines et varias voluptatum inlecebras, ne longius progrediar,
            praetermitto illuc
            transiturus quod quidam per ampla spatia urbis subversasque silices sine periculi metu properantes equos
            velut publicos signatis quod dicitur calceis agitant, familiarium agmina tamquam praedatorios globos post
            terga trahentes ne Sannione quidem, ut ait comicus, domi relicto. quos imitatae matronae complures opertis
            capitibus et basternis per latera civitatis cuncta discurrunt.
        </p>
        <p class="text-justify"> Alios autem dicere aiunt multo etiam inhumanius (quem locum breviter paulo ante
            perstrinxi) praesidii
            adiumentique causa, non benevolentiae neque caritatis, amicitias esse expetendas; itaque, ut quisque minimum
            firmitatis haberet minimumque virium, ita amicitias appetere maxime; ex eo fieri ut mulierculae magis
            amicitiarum praesidia quaerant quam viri et inopes quam opulenti et calamitosi quam ii qui putentur beati.
        </p>
    </div>
</div>

<script src="<c:url value="/inc/js/popper.min.js" /> " type="text/javascript"></script>
<script type="text/javascript" src="<c:url value="/inc/js/bootstrap.min.js" />"></script>
</body>
</html>