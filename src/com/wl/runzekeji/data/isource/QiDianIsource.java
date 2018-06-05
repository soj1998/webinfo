package com.wl.runzekeji.data.isource;
import com.wl.runzekeji.data.entity.QiDianEntity;
import java.util.List;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;

public interface QiDianIsource {
    Flowable<List<QiDianEntity>> getQiDians();

    Flowable<QiDianEntity> getQiDian(@NonNull String qiDianId);

    void saveQiDian(@NonNull QiDianEntity qidian);

    void refreshQiDian();

    void deleteAllQiDians();

    void deleteQiDian(@NonNull String qiDianId);
}

