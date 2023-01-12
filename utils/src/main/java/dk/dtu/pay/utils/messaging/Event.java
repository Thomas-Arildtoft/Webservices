package dk.dtu.pay.utils.messaging;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    private static final long serialVersionUID = 4986172999588690076L;
    private Object[] arguments = null;

    public <T> T getArgument(int i, Class<T> cls) {
        // The hack is needed because of Events are converted
        // to JSon for transport. Because JSon does not store
        // the class of an Object, when deserializing the arguments
        // of an Event, LinkedTreeLists are returned, which cannot be
        // cast to real objects or converted to JSonObjects.
        // The trick is to generated a JSon string from the argument and
        // then parse that string back to the class one needs.
        // This also works, for tests, where the arguments to an Event contain
        // the original objects.
        var gson = new Gson();
        var jsonString = gson.toJson(arguments[i]);
        return gson.fromJson(jsonString, cls);
    }

}
