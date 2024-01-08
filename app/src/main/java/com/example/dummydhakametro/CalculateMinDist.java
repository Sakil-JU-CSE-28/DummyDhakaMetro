package com.example.dummydhakametro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

public class CalculateMinDist extends AppCompatActivity {
    private StationDbHelper stationDbHelper;
    Set<Node>createStation = new HashSet<>();
    Map<String,Node>nodeDetector = new HashMap<>();
    Map<Node,Vector<Node>>AdjacencyList = new HashMap<>();
    RecyclerView recyclerView;
    MyAdapter adapter;
    public void createAdjacencyList(){
        InputStream inputStream = getResources().openRawResource(R.raw.stations);
        Scanner scanner = new Scanner(inputStream);
        String cur1 = "",cur2 = "";

        while (scanner.hasNext()) {
            cur1 = "";
            cur2 = "";
            String token = scanner.next(); // Tokenizing by words
            for(int i = 0;i < token.length();i++){
                if(token.charAt(i) == '-'){
                    cur1 = cur2;
                    cur2 = "";
                }
                else
                    cur2 += token.charAt(i);
            }

            Node source = nodeDetector.get(cur1);
            Node destination = nodeDetector.get(cur2);

            if(AdjacencyList.containsKey(source)){
                 AdjacencyList.get(source).add(destination);
            }
            else{
                Vector<Node>nextNode = new Vector<>();
                nextNode.add(destination);
                nextNode.add(destination);
                AdjacencyList.put(source,nextNode);
            }
            if(AdjacencyList.containsKey(destination)){
                AdjacencyList.get(destination).add(source);
            }
            else{
                Vector<Node>nextNode = new Vector<>();
                nextNode.add(destination);
                nextNode.add(destination);
                AdjacencyList.put(destination,nextNode);
            }
        }
        scanner.close();
    }
    @SuppressLint({"MissingInflatedId", "SuspiciousIndentation"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_min_dist);
        Intent intent = getIntent();
        String source = intent.getStringExtra("Source");
        String destination = intent.getStringExtra("Destination");
        String token = intent.getStringExtra("Token");
        stationDbHelper = new StationDbHelper(this);

        Set<String> stations = stationDbHelper.getAllStations();

        for(String i : stations){
            Node node = new Node(i);
            nodeDetector.put(i,node);
            createStation.add(node);
        }

        createAdjacencyList();
        for(Node i : createStation){
            for(Node j : AdjacencyList.get(i)){
                int distance = getRandomDistand(token);
                i.addDestination(j,distance);
            }
        }
        Graph graph = new Graph();
        for(Node i : createStation){
            graph.addNode(i);
        }

        graph = calculateShortestPathFromSource(graph,nodeDetector.get(source));
        recyclerView = findViewById(R.id.recyclerView);

        Set<Node>calCulatedNode = graph.getNodes();
        for(Node i : calCulatedNode){
            nodeDetector.put(i.getName(),i);
        }

        Vector<String>station = new Vector<>();
        for(Node i : nodeDetector.get(destination).getShortestPath()) station.add(i.getName());
        station.add(destination);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(station);
        recyclerView.setAdapter(adapter);
        TextView tv = findViewById(R.id.tv);
        if(token.equals("Token1")) tv.setText("Total min distance from " + source +" to " + destination + " = " + String.valueOf(nodeDetector.get(destination).getDistance()) + " KM");
        else tv.setText("Total min cost to travel from " + source +" to " + destination + " = " + String.valueOf(nodeDetector.get(destination).getDistance()) + "-/");
    }

    public static Graph calculateShortestPathFromSource(Graph graph, Node source) {
        source.setDistance(0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry< Node, Integer> adjacencyPair:
                    currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    CalculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
        return graph;
    }

    private static Node getLowestDistanceNode(Set < Node > unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node: unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private static void CalculateMinimumDistance(Node evaluationNode,
                                                 Integer edgeWeigh, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    public  int getRandomDistand(String token){
        int min = 1;
        int max = 5;
        if(token.equals("Token2")){
            min = 10;
            max = 50;
        }
        Random random = new Random();
        int randomNumber = random.nextInt(max - min + 1) + min;
        return randomNumber;
    }

}