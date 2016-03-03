<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         import="twittMapWeb.EsSearch, twittMapWeb.Keywords, java.util.*"%>

<!DOCTYPE html>
<html>
<head>
    <title>Tweet Map</title>
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1.0">
    <style>
        html, body {
            height: 100%;
        }

        #floating-panel {
            top: 0;
            right: 0;
            position: absolute;
            z-index: 1000;
        }
        #map {
            height: 100%;
        }
        #coord {
            padding: 5px;
            color: white;
            background-color: black;
        }
    </style>
    <script src="https://code.jquery.com/jquery-1.12.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.1.8/semantic.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.1.8/semantic.min.css">
</head>

<body>
<div id="floating-panel">
    <div class="ui segment">
        <button onclick="showMark('food');" class="ui inverted violet button">Food</button>
        <button onclick="showMark('job');" class="ui inverted red button">Job</button>
        <button onclick="showMark('love');" class="ui inverted orange button">Love</button>
        <button onclick="showMark('fashion');" class="ui inverted yellow button">Fashion</button>
        <button onclick="showMark('lol');" class="ui inverted olive button">LOL</button>

        <div class="ui buttons">
            <button onclick="showMore();" class="ui button">Show More</button>
            <div class="or"></div>
            <button id="clearAllButton" onclick="clearData();" class="ui button">Clear All</button>
        </div>
    </div>
</div>

<div id="map"></div>
<div id="coord"></div>


<script src="/socket.io/socket.io.js"></script>
<script src="map.js"></script>>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBLh6Ju5C5aVc0dnbheLlXkHbQqP2xY4KY&callback=initMap"></script>
<div style="display: none" id="myModel" class="ui basic modal">
    <div class="header">Instruction</div>
    <div class="content">
        <p>Click bottom tags for keyword search.</p>
        <p>Click markers for details.</p>
        <p>Left click map for geo-spatial search.</p>
        <p>Each keyword search will show the last 50 twitters, click Show More for more information.</p>
    </div>
    <div class="actions">
        <div class="ui approve button">Ok</div>
    </div>
</div>