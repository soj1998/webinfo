package com.wl.runzekeji.data.repository;


import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.wl.runzekeji.data.entity.QiDianEntity;
import com.wl.runzekeji.data.isource.QiDianIsource;

public class QiDianRep implements QiDianIsource {
	@Nullable
    private static QiDianRep INSTANCE = null;

    @NonNull
    private final QiDianIsource mRemoteDataSource;

    @NonNull
    private final QiDianIsource mLocalDataSource;

    @Nullable
    Map<String, QiDianEntity> mCachedQiDians;
	
    boolean mCacheIsDirty = false;

    // Prevent direct instantiation.
    private QiDianRep(@NonNull QiDianIsource remoteDataSource,
                            @NonNull QiDianIsource localDataSource) {
        mRemoteDataSource = remoteDataSource;
        mLocalDataSource = localDataSource;
    }


    public static QiDianRep getInstance(@NonNull QiDianIsource remoteDataSource,
                                              @NonNull QiDianIsource localDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new QiDianRep(remoteDataSource, localDataSource);
        }
        return INSTANCE;
    }


    public static void destroyInstance() {
        INSTANCE = null;
    }
    
	public Flowable<List<QiDianEntity>> getQiDians() {
		// TODO Auto-generated method stub
		return null;
	}

	public Flowable<QiDianEntity> getQiDian(String qiDianId) {
		final QiDianEntity cachedQiDian = getQiDianWithId(qiDianId);
        if (cachedQiDian != null) {
        	return Flowable.just(cachedQiDian);
        }

        // Load from server/persisted if needed.

        
        if (mCachedQiDians == null) {
        	mCachedQiDians = new LinkedHashMap<String, QiDianEntity>();
        }

        // Is the task in the local data source? If not, query the network.
        Flowable<QiDianEntity> local = null;
        Flowable<QiDianEntity> remote = mRemoteDataSource
                .getQiDian(qiDianId)
                .doOnNext(qidian -> {
                	mLocalDataSource.saveQiDian(qidian);
                	mCachedQiDians.put(qidian.getId(), qidian);

                });

        return Flowable.concat(local, remote)
                .firstElement()
                .toFlowable();
	}
	@Nullable
	private QiDianEntity getQiDianWithId(@NonNull String qiDianId) {
		if (mCachedQiDians == null || mCachedQiDians.isEmpty()) {
            return null;
        } else {
            return mCachedQiDians.get(qiDianId);
        }
	}


	public void saveQiDian(QiDianEntity qidian) {
		// TODO Auto-generated method stub

	}

	public void refreshQiDian() {
		// TODO Auto-generated method stub

	}

	public void deleteAllQiDians() {
		// TODO Auto-generated method stub

	}

	public void deleteQiDian(String qiDianId) {
		// TODO Auto-generated method stub

	}

}
