package com.hammasir.routingreport.component;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.WKTWriter;
import org.springframework.stereotype.Component;

@Component
public class GeometryHandler {

    public Geometry createGeometry(String location) {
        WKTReader reader = new WKTReader();
        try {
            Geometry geometry = reader.read(location);
            geometry.setSRID(4326);
            return geometry;
        } catch (Exception e) {
            throw new IllegalArgumentException("Location is NOT valid!");
        }
    }

    public String createWkt(Geometry geometry) {
        WKTWriter wktWriter = new WKTWriter();
        try {
            return wktWriter.write(geometry);
        } catch (Exception e) {
            throw new IllegalArgumentException("Location is NOT valid!");
        }
    }
}
