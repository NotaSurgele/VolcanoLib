import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class SaveListener implements ActionListener {

    private void setJSONArray(int[][] array, JSONArray jsonArr, JSONObject map, String name)
    {
        int sizeX = array.length;
        int sizeY = array[0].length;

        for (int line = 0; line != sizeX; line++) {

            JSONArray tmp = new JSONArray();

            for (int each = 0; each != sizeY; each++) {
                tmp.add(each, array[line][each]);
            }
            jsonArr.add(line, tmp);
        }
        map.put(name, jsonArr);
    }

    private JSONObject getGlobalObject()
    {
        JSONObject obj = null;

        File f = new File("assets/data/Rooms.json");

        if (f.length() == 0)
            return new JSONObject();
        else {
            try {
                Object tmp = new JSONParser().parse(new FileReader(f));
                obj = (JSONObject) tmp;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    private void writeJSONData(int[][] wall, int[][] floor, int[][] prop)
    {
        JSONObject globalOBJ = getGlobalObject();
        JSONObject obj = new JSONObject();

        JSONArray JSONFloor = new JSONArray();
        JSONArray JSONWall = new JSONArray();
        JSONArray JSONProp = new JSONArray();

        this.setJSONArray(wall, JSONFloor, obj, "Wall");
        this.setJSONArray(floor, JSONWall, obj, "Floor");
        this.setJSONArray(prop, JSONProp, obj, "Prop");

        globalOBJ.put(String.valueOf(globalOBJ.size() + 1), obj);

        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter write = null;

        try {
            fw = new FileWriter("assets/data/Rooms.json", false);
            bw = new BufferedWriter(fw);
            write = new PrintWriter(bw);

            write.println(globalOBJ.toJSONString());
            write.flush();
            fw.close();
            bw.close();
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Save s = (Save) e.getSource();
        JPanel jp = s.getContentPane();
        int[][] wall = ((LayerButton) jp.getComponentAt(832, 400)).getLayer();
        int[][] floor = ((LayerButton) jp.getComponentAt(832, 432)).getLayer();
        int[][] prop = ((LayerButton) jp.getComponentAt(832, 464)).getLayer();

        this.writeJSONData(wall, floor, prop);
    }
}
