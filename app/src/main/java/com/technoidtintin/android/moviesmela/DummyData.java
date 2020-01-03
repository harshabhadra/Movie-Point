package com.technoidtintin.android.moviesmela;

import com.technoidtintin.android.moviesmela.Model.ListItem;

import java.util.ArrayList;
import java.util.List;

public class DummyData {

    public List<ListItem> getPopularMovies() {
        String image = "http://image.tmdb.org/t/p/w185" + "/pVGzV02qmHVoKx9ehBNy7m2u5fs.jpg";
        List<ListItem> popularList = new ArrayList<>();
        popularList.add(new ListItem(1, "Add Astra",image ));
        popularList.add(new ListItem(1, "Add Astra",image ));
        popularList.add(new ListItem(1, "Add Astra",image ));
        popularList.add(new ListItem(1, "Add Astra",image ));
        popularList.add(new ListItem(1, "Add Astra",image ));
        popularList.add(new ListItem(1, "Add Astra",image ));
        popularList.add(new ListItem(1, "Add Astra",image ));
        return popularList;
    }

    public List<ListItem>getTopRatedList(){
        String image = "http://image.tmdb.org/t/p/w185" + "/9O7gLzmreU0nGkIB6K3BsJbzvNv.jpg";
        List<ListItem>topList = new ArrayList<>();
        topList.add(new ListItem(1,"Top rated",image));
        topList.add(new ListItem(1,"Top rated",image));
        topList.add(new ListItem(1,"Top rated",image));
        topList.add(new ListItem(1,"Top rated",image));
        topList.add(new ListItem(1,"Top rated",image));
        topList.add(new ListItem(1,"Top rated",image));
        topList.add(new ListItem(1,"Top rated",image));
        topList.add(new ListItem(1,"Top rated",image));
        topList.add(new ListItem(1,"Top rated",image));
        return topList;
    }
}
