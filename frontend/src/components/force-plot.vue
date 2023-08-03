<script setup>
import {onMounted} from "vue";
import * as d3 from 'd3';
import { toolColor } from "@/utils/global";

const props = defineProps(['metadata', 'data'])

onMounted(() => {
  // Specify the dimensions of the chart.
  const element = document.getElementById("plot");
  const width = element.clientWidth;
  const height = window.innerHeight //element.clientHeight;

  const origNodes = []
  const origLinks = []

  for (const meta of props.metadata) {
    if (meta.spdx_exists) {
      let item = {}
      item.id = meta.generator + '-' + meta.mode
      item.group = meta.mode
      origNodes.push(item)
    }
  }

  for (const deps of props.data) {
    let item = {}
    item.id = deps.name
    item.group = 'dependencies'
    origNodes.push(item)
    for (const gens of deps.generator) {
      let link = {}
      link.source = deps.name
      link.target = gens
      origLinks.push(link)
    }
  }

  // The force simulation mutates links and nodes, so create a copy
  // so that re-evaluating this cell produces the same result.
  const links = origLinks.map(d => ({...d}));
  const nodes = origNodes.map(d => ({...d}));

  // Create a simulation with several forces.
  const simulation = d3.forceSimulation(nodes)
      .force("link", d3.forceLink(links).id(d => d.id))
      .force("charge", d3.forceManyBody().distanceMax(500))
      .on("tick", ticked);

  // Create the SVG container.
  const svg = d3.select("#plot")
      .attr("width", width)
      .attr("height", height)
      .attr("viewBox", [-width / 2, -height / 2, width, height])
      .attr("style", "width: 100%; height: 100%;");

  // Add a line for each link, and a circle for each node.
  const link = svg.append("g")
      .attr("stroke", "#999")
      .attr("stroke-opacity", 0.6)
      .selectAll()
      .data(links)
      .join("line")
      .attr("stroke-width", 1);

  const node = svg.append("g")
      .attr("stroke", "#fff")
      .attr("stroke-width", 1.5)
      .selectAll()
      .data(nodes)
      .join("circle")
      .attr("r", 5)
      .attr("fill", d => toolColor(d.group));

  node.append("title")
      .text(d => d.id);

  // Add a drag behavior.
  node.call(d3.drag()
      .on("start", dragstarted)
      .on("drag", dragged)
      .on("end", dragended));

  // Set the position attributes of links and nodes each time the simulation ticks.
  function ticked() {
    link
        .attr("x1", d => d.source.x)
        .attr("y1", d => d.source.y)
        .attr("x2", d => d.target.x)
        .attr("y2", d => d.target.y);
    node
        .attr("cx", d => d.x)
        .attr("cy", d => d.y);
  }

  // Reheat the simulation when drag starts, and fix the subject position.
  function dragstarted(event) {
    if (!event.active) simulation.alphaTarget(0.3).restart();
    event.subject.fx = event.subject.x;
    event.subject.fy = event.subject.y;
  }

  // Update the subject (dragged node) position during drag.
  function dragged(event) {
    event.subject.fx = event.x;
    event.subject.fy = event.y;
  }

  // Restore the target alpha so the simulation cools after dragging ends.
  // Unfix the subject position now that it’s no longer being dragged.
  function dragended(event) {
    if (!event.active) simulation.alphaTarget(0);
    event.subject.fx = null;
    event.subject.fy = null;
  }

  // When this cell is re-run, stop the previous simulation. (This doesn’t
  // really matter since the target alpha is zero and the simulation will
  // stop naturally, but it’s a good practice.)
  // invalidation.then(() => simulation.stop());

  return svg.node();
})
</script>

<template>
  <svg id="plot" style="width:100%; height:100%"></svg>
</template>

<style scoped>

</style>