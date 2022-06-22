package view;

import com.sothawo.mapjfx.*;
import com.sothawo.mapjfx.event.MapViewEvent;
import com.sothawo.mapjfx.offline.OfflineCache;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import viewModel.ViewModel;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;


public class WorldMapController extends BaseController {
        @FXML
        MapView newMap;
        WMSParam wmsParam;
        XYZParam xyzParam;
        private  Marker marker;

        HashMap<Integer,Marker> markers;

        @Override
        public void init(ViewModel vm, Node root) throws Exception {
                viewModel =vm;
                markers = new HashMap<>();
        }

        @Override
        public void updateUi(Object obj) {
                SerializableCommand command = (SerializableCommand) obj;
                if(command.getCommandName().intern()=="mapData"){
                        HashMap<Integer,SerializableCommand> agentsRowData = (HashMap<Integer, SerializableCommand>) command.getObject();
                        addMarkers(agentsRowData);
                }
                else if(command.getCommandName().intern()=="noAgent"){
                        markers.forEach((k,v)->{
                                Platform.runLater(()->newMap.removeMarker(v));
                                markers.remove(k);
                        });
                }

        }

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                wmsParam = new WMSParam()
                        .setUrl("http://ows.terrestris.de/osm/service")
                        .addParam("layers", "OSM-WMS");

                xyzParam = new XYZParam()
                        .withUrl("https://server.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer/tile/{z}/{y}/{x})")
                        .withAttributions("'Tiles &copy; <a href=\"https://services.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer\">ArcGIS</a>'");
        }

        public void initMapAndControls(Projection projection) {
                final OfflineCache offlineCache = newMap.getOfflineCache();
                final String cacheDir = System.getProperty("java.io.tmpdir") + "/mapjfx-cache";
                marker = new Marker(getClass().getResource("images/aircraft.png"), 30, 30).setPosition(new Coordinate(63.991837, -22.605427))
                        .setVisible(false);
                marker.setRotation(-5);
                newMap.initializedProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue) {
                                afterMapIsInitialized();
                        }
                });
                newMap.setMapType(MapType.OSM);
                setupEventHandlers();
                newMap.setAnimationDuration(0);
               newMap.initialize(Configuration.builder()
                        .projection(projection)
                        .showZoomControls(true)
                        .build());
        }

        private void afterMapIsInitialized() {
                newMap.setZoom(14);
                newMap.setCenter(new Coordinate(63.991837, -22.605427));
                newMap.addMarker(marker);
                newMap.addMapCircle(new MapCircle(new Coordinate(63.991837, -22.605427), 1_000).setVisible(true));

        }

        private void setupEventHandlers() {
                // add an event handler for singleclicks, set the click marker to the new position when it's visible
                newMap.addEventHandler(MapViewEvent.MAP_CLICKED, event -> {
                        event.consume();
                        final Coordinate newPosition = event.getCoordinate().normalize();
                        System.out.println("Event: map clicked at: " + newPosition);
//                        if (checkDrawPolygon.isSelected()) {
//                                handlePolygonClick(event);
//                        }
//                        if (markerClick.getVisible()) {
//                                final Coordinate oldPosition = markerClick.getPosition();
//                                if (oldPosition != null) {
//                                        animateClickMarker(oldPosition, newPosition);
//                                } else {
//                                        markerClick.setPosition(newPosition);
//                                        // adding can only be done after coordinate is set
//                                        mapView.addMarker(markerClick);
//                                }
//                        }
                });
        }
        private void addMarkers(HashMap<Integer,SerializableCommand> agentsRowData){
                agentsRowData.forEach((k,v)->{
                        if(v.getData()==null)
                                return;
                        String[] tokens = v.getData().split(",");
                        if(markers.get(k)!=null){
                                Platform.runLater(()->{
                                        markers.get(k).setPosition(new Coordinate((double) Float.parseFloat(tokens[13]), (double) Float.parseFloat(tokens[14]))).setRotation((int) Float.parseFloat(tokens[18]));
                                });
                                return;
                        }
                        Marker tempMarker = new Marker(getClass().getResource("images/aircraft.png"), 30, 30)
                                .setPosition(new Coordinate((double) Float.parseFloat(tokens[13]), (double) Float.parseFloat(tokens[14])))
                                .setRotation((int) Float.parseFloat(tokens[18]));
                        tempMarker.setVisible(true);
                        MapLabel label = new MapLabel(v.commandName, 10, -10).setVisible(true).setCssClass("black-label");
                        tempMarker.attachLabel(label);
                        markers.put(k,tempMarker);
                        Platform.runLater(()->newMap.addMarker(tempMarker));
                });
                markers.forEach((k,v)->{
                        if(!agentsRowData.containsKey(k)){
                                Platform.runLater(()->newMap.removeMarker(v));
                                markers.remove(k);
                        }
                });
        }

}