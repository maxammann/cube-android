package max.cube;


import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class Generator {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1000, "max.cube.dao");

        Entity note = schema.addEntity("Alarm");
        note.addIdProperty();
        note.addStringProperty("name").notNull();
        note.addIntProperty("wake");
        note.addBooleanProperty("enabled");
        note.implementsInterface("de.greenrobot.dao.HasId<Long>");

        new DaoGenerator().generateAll(schema, args[0]);
    }
}
