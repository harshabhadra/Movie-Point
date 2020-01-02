package com.technoidtintin.android.moviesmela;

import com.technoidtintin.android.moviesmela.Model.MovieItem;

import java.util.ArrayList;
import java.util.List;

public class DummyData {

    public List<MovieItem> getPopularMovies() {
        String image = "http://image.tmdb.org/t/p/w185" + "/pVGzV02qmHVoKx9ehBNy7m2u5fs.jpg";
        List<MovieItem> popularList = new ArrayList<>();
        popularList.add(new MovieItem(1, "Add Astra",image ));
        popularList.add(new MovieItem(1, "Add Astra",image ));
        popularList.add(new MovieItem(1, "Add Astra",image ));
        popularList.add(new MovieItem(1, "Add Astra",image ));
        popularList.add(new MovieItem(1, "Add Astra",image ));
        popularList.add(new MovieItem(1, "Add Astra",image ));
        popularList.add(new MovieItem(1, "Add Astra",image ));
        return popularList;
    }

    public List<MovieItem>getTopRatedList(){
        String image = "http://image.tmdb.org/t/p/w185" + "/9O7gLzmreU0nGkIB6K3BsJbzvNv.jpg";
        List<MovieItem>topList = new ArrayList<>();
        topList.add(new MovieItem(1,"Top rated",image));
        topList.add(new MovieItem(1,"Top rated",image));
        topList.add(new MovieItem(1,"Top rated",image));
        topList.add(new MovieItem(1,"Top rated",image));
        topList.add(new MovieItem(1,"Top rated",image));
        topList.add(new MovieItem(1,"Top rated",image));
        topList.add(new MovieItem(1,"Top rated",image));
        topList.add(new MovieItem(1,"Top rated",image));
        topList.add(new MovieItem(1,"Top rated",image));
        return topList;
    }
}
