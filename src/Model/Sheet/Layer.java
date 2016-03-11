package Model.Sheet;

import Interface.Drawing.DrawObject;

/**
 * Created by 67827 on 18.01.2016.
 */
public class Layer {

    private String name;
    private Sheet sheet;
    private SheetController sheetController;
    private int orderNumber;

    public Layer(int orderNumber) {
        this.orderNumber = orderNumber;
        this.name = "Layer"+orderNumber;
        sheet = new Sheet();
        sheetController = new SheetController();
        sheet.setSheetController(sheetController);
        sheetController.setSheet(sheet);
    }

    public boolean add(DrawObject drawObject){
        boolean couldAdd = sheet.getChildren().add(drawObject.getVisual());
        sheet.getCenterPoint().getVisual().toFront();
        return couldAdd;
    }

    public boolean remove(DrawObject drawObject){
        return sheet.getChildren().remove(drawObject.getVisual());
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public SheetController getSheetController() {
        return sheetController;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
