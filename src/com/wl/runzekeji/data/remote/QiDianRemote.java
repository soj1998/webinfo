package com.wl.runzekeji.data.remote;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.wl.runzekeji.data.entity.QiDianEntity;
import com.wl.runzekeji.data.isource.QiDianIsource;

public class QiDianRemote implements QiDianIsource {
    private static QiDianRemote INSTANCE;

    private static final int SERVICE_LATENCY_IN_MILLIS = 5000;

    private final static Map<String, QiDianEntity> QiDian_SERVICE_DATA;

    static {
    	QiDian_SERVICE_DATA = new LinkedHashMap<String, QiDianEntity>(2);
        addQiDian("Build tower in Pisa", "Ground looks good, no foundation work required.");
        addQiDian("Finish bridge in Tacoma", "Found awesome girders at half the cost!");
    }

    public static QiDianRemote getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new QiDianRemote();
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private QiDianRemote() {
    }

    private static void addQiDian(String title, String description) {
    	QiDianEntity newQiDian = new QiDianEntity();
    	newQiDian.setId(title);
    	QiDian_SERVICE_DATA.put(description,newQiDian);
    }

	@Override
	public Flowable<List<QiDianEntity>> getQiDians() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flowable<QiDianEntity> getQiDian(@NonNull String qiDianId) {
		final QiDianEntity qidian = QiDian_SERVICE_DATA.get(qiDianId);
        if (qidian != null) {
            return Flowable.just(qidian).delay(SERVICE_LATENCY_IN_MILLIS, TimeUnit.MILLISECONDS);
        } else {
            return Flowable.empty();
        }
	}

	@Override
	public void saveQiDian(QiDianEntity qidian) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refreshQiDian() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllQiDians() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteQiDian(String qiDianId) {
		// TODO Auto-generated method stub

	}

}
