<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 15.12.2019
  Time: 21:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="by.training.machine.monitoring.app.ApplicationConstant" language="java" %>
<c:if test="${ApplicationConstant.SECURITY_SERVICE.isLogIn(pageContext.request.session)
and pageContext.findAttribute('commandName').equalsIgnoreCase('showMachineStatus')}">
    <fmt:message key="sensors.value.msg" var="sensors" scope="page"/>
    <fmt:message key="date.msg" var="date" scope="page"/>
    <fmt:message key="value.msg" var="value" scope="page"/>
    <div class="demo-charts mdl-color--white mdl-shadow--2dp mdl-cell mdl-cell--12-col mdl-grid" style="margin: 2px;">
        <div class="demo-charts mdl-color--white mdl-cell mdl-cell--12-col mdl-grid" style="margin: 2px;padding-bottom: 0px;">
            <div class="mdl-cell mdl-cell--12-col" style="position: relative;padding: 2px;">
                <span class="mdl-layout-title" style="position: absolute;top: 50%;left: 50%;transform: translate(-50%, -50%);font-size: 16px;font-weight: 500;color: #a2a4a2;">
                    <c:out value="${sensors}"/>
                </span>
            </div>
        </div>
        <div class="demo-charts mdl-color--white mdl-cell mdl-cell--12-col mdl-grid" style="margin: 2px;padding-bottom: 0px;padding-top: 0px;">
            <script type="text/javascript">
                window.onload = function () {
                    var chart = new CanvasJS.Chart("chartContainer", {
                        theme: "light2",
                        title: {
                            text: "${sensors}"
                        },
                        axisX: {
                            title: "${date}",
                            valueFormatString: "DD-MMM",
                        },
                        axisY: {
                            title: "${value}"
                        },
                        toolTip: {
                            shared: true,
                            reversed: true,
                        },
                        legend: {
                            cursor: "pointer",
                            itemclick: toggleDataSeries
                        },
                        data: [{
                            type: "line",
                            yValueFormatString: "###,## pct",
                            showInLegend: true,
                            name: "Fuel level",
                            dataPoints: ${fuelLevelPoints}
                        }, {
                            type: "line",
                            yValueFormatString: "###,## bar",
                            showInLegend: true,
                            name: "Oil pressure",
                            dataPoints: ${oilPressurePoints}
                        }, {
                            type: "line",
                            yValueFormatString: "###,## pct",
                            showInLegend: true,
                            name: "Oil level",
                            dataPoints: ${oilLevelPoints}
                        }, {
                            type: "line",
                            yValueFormatString: "###,##0 deg",
                            showInLegend: true,
                            name: "Coolant temp",
                            dataPoints: ${coolantTempPoints}
                        }, {
                            type: "line",
                            showInLegend: true,
                            markerColor: "red",
                            markerSize: 8,
                            markerType: "cross",
                            name: "Error",
                            dataPoints: ${errorTempPoints}
                        }]
                    });
                    chart.render();

                    function toggleDataSeries(e) {
                        if (typeof (e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
                            e.dataSeries.visible = false;
                        } else {
                            e.dataSeries.visible = true;
                        }
                        chart.render();
                    }
                }
            </script>
            <div id="chartContainer" style="height: 370px; width: 100%;"></div>
        </div>
    </div>
</c:if>

