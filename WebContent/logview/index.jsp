<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Log Viewer</title>
<link href="styles/ng-websocket-log-viewer-1.0.0.css" rel="stylesheet" />
<link href="styles/web-viewer.css" rel="stylesheet" />
<link rel="shortcut icon" href="//angularjs.org/favicon.ico"
	type="image/x-icon">
</head>
<body ng-app="nglog-websockeet-demo" ng-controller="logController">

	<div id="header">
		<label>Number of lines</label><input type="number"
			ng-model="numberOfLines" ng-model-options="{ debounce: 500 }"
			ng-change="setLineCount(numberOfLines)" />
		<button ng-click="pause()" ng-class="{blink : paused}">Pause</button>
		<label>Add source</label><input id="add-source" expand-on-focus="400"
			type="text" ng-model="newSource" /><input type="color"
			ng-model="newSoureColor" />
		<button ng-click="addSource(newSource, newSoureColor)">Add</button>
		<label>Filter Regex</label><input id="regex" expand-on-focus="200"
			type="text" ng-model="filterExpression"
			ng-model-options="{ debounce: 500 }"
			ng-change="filter(filterExpression)" /> <label>Highlight</label><input
			id="highlight" expand-on-focus="200" type="text"
			ng-model="highlightText" ng-model-options="{ debounce: 500 }"
			ng-change="highlight(highlightText)" />
	</div>

	<websocket-log-viewer id="log-container"></websocket-log-viewer>

	<script src="scripts/angular.min.js"></script>
	<script src="scripts/ng-websocket-log-viewer-1.0.0.js"></script>
	<script src="scripts/web-viewer.js"></script>
</body>
</html>