/*
    Widget specific JS (ie: init scripts of 
    plugins used in the widget) go here
*/

$(document).ready(function() {
     //Get from JSON data and build

     d3.json('http://revox.io/json/min_sales_chart.json', function(data) {
         //********************************** BEGIN CHART **************************************//
         pVisitsChartMiniConfig = nv.models.lineChart()
             .transitionDuration(350)
             .margin({
                 top: 10,
                 right: -10,
                 bottom: 0,
                 left: -10
             })
             .showLegend(false) //Show the legend, allowing users to turn on/off line series.
         .showYAxis(false) //Show the y-axis
         .showXAxis(false) //Show the x-axis
         .color(['#6d5eac']) //
         .useInteractiveGuideline(false);
         pVisitsChartMiniConfig.lines.forceY([0], 8); //Force Chart start with 0 not the min y point
         pVisitsChartMiniConfig.tooltipContent(function(key, x, y) {
             return '<p class="text-left"><span class="text-success bold">+' + y + '</span><br> <span class="small hinted-text">' + x + '</span></p>';
         });
         nv.addGraph(function() {

             d3.select('#pages-visits-chart-mini svg') //Select the <svg> element you want to render the chart in.   
             .datum(data.pageViews) //Populate the <svg> element with chart data...
             .transition().duration(500)
                 .call(pVisitsChartMiniConfig);
             //Responsive Handler
             nv.utils.windowResize(pVisitsChartMiniConfig.update);

             return pVisitsChartMiniConfig;
         }, function() {
             setTimeout(function() {
                 $('#pages-visits-chart-mini .nv-lineChart circle.nv-point').attr("r", "3");
             }, 500);
         });

    });

});