package Model.Sheet;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Point2D;

/**
 * Created by 67827 on 18.01.2016.
 */
public class SheetController {

    //Sheet Info(Size, Centerpoint)
    private ChangeListener<Number> widthListener;
    private ChangeListener<Number> heightListener;
    private double centerX;
    private double centerY;
    //Sheet
    private Sheet sheet;

    public SheetController() {

        widthListener = (observable, oldValue, newValue) -> {
            resizeHandle("width", newValue.doubleValue());
        };
        heightListener = (observable, oldValue, newValue) ->{
            resizeHandle("height",newValue.doubleValue());
        };

    }

    public void resizeHandle(String key, double value){
        if(key.equalsIgnoreCase("width")){
            centerX = value/2;
        }else{
            centerY = value/2;
        }
        sheet.getCenterPoint().updatePosition(new Point2D(centerX,centerY));
        //System.out.println("Sheet: "+sheet.getWidth()+" : "+sheet.getHeight()+" Center: "+centerX+" : "+centerY);
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public ChangeListener<Number> getWidthListener() {
        return widthListener;
    }

    public ChangeListener<Number> getHeightListener() {
        return heightListener;
    }

}
