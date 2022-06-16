module com.example.front {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.graphics;
    requires Medusa;

//    opens com.example.front to javafx.fxml;
//    exports com.example.front;

    opens view to javafx.fxml;
    exports view;

    exports view.Charts;
}
