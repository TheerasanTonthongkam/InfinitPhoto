package com.playtech.infinitphoto;

import com.playtech.infinitphoto.view.activities.main.MainActivityViewModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(MockitoJUnitRunner.class)
public class MainActivityViewModelUnitTest {

    @Test
    public void getHashTag_whenInputName_hashTagCorrect() {
        MainActivityViewModel viewModel = new MainActivityViewModel();
        viewModel.setAlbumName("My Name");
        String hashTag = viewModel.getHashTag();
        String expectString = "#myname";

        assertThat(hashTag, equalTo(expectString));
    }
}
