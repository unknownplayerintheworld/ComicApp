package hung.deptrai.comicapp.views;

import android.app.Application;

public class ComicApplication extends Application {
    @Override
    public void onTerminate() {
        super.onTerminate();
        // Thực hiện các hoạt động khi ứng dụng bị hủy hoàn toàn
    }
}
