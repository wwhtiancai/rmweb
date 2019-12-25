package com.tmri.rfid.service.impl;

import com.tmri.framework.web.support.UserState;
import com.tmri.rfid.bean.ReviewRecord;
import com.tmri.rfid.common.ReviewStatus;
import com.tmri.rfid.mapper.ReviewRecordMapper;
import com.tmri.rfid.service.ReviewRecordService;
import com.tmri.rfid.util.MapUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Joey on 2016/10/11.
 */
@Service
public class ReviewRecordServiceImpl implements ReviewRecordService {

    @Autowired
    private ReviewRecordMapper reviewRecordMapper;

    @Override
    public void approve(String ywdm, Long ywxh, String yj) {
        review(ywdm, ywxh, yj, ReviewStatus.APPROVED);
    }

    @Override
    public void reject(String ywdm, Long ywxh, String yj) {
        review(ywdm, ywxh, yj, ReviewStatus.REJECT);

    }

    private void review(String ywdm, Long ywxh, String yj, ReviewStatus jg) {
        ReviewRecord reviewRecord = new ReviewRecord();
        reviewRecord.setZt(jg.getStatus());
        reviewRecord.setYwdm(ywdm);
        reviewRecord.setYwxh(ywxh);
        reviewRecord.setYj(yj);
        reviewRecord.setShrdh(UserState.getUser().getYhdh());
        reviewRecord.setShsj(new Date());
        reviewRecordMapper.create(reviewRecord);
    }

    @Override
    public List<ReviewRecord> fetch(String ywdm, Long ywxh) {
        return reviewRecordMapper.queryByCondition(MapUtilities.buildMap("ywdm", ywdm, "ywxh", ywxh));
    }
}
