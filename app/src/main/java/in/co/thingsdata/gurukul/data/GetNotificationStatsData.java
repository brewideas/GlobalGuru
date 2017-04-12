package in.co.thingsdata.gurukul.data;

import java.util.ArrayList;

import in.co.thingsdata.gurukul.data.common.CommonDetails;
import in.co.thingsdata.gurukul.data.common.NotificationReplyDetail;

/**
 * Created by Vikas on 2/24/2017.
 */

public class GetNotificationStatsData {
    private String mAccessToken;
    private String mNotificationId;
    private ArrayList<NotificationReplyDetail> mRepliedYesTeacher = new ArrayList<>();
    private ArrayList<NotificationReplyDetail> mRepliedNoTeacher = new ArrayList<>();
    private ArrayList<NotificationReplyDetail> mRepliedYesStudent = new ArrayList<>();
    private ArrayList<NotificationReplyDetail> mRepliedNoStudent = new ArrayList<>();
    private ArrayList<NotificationReplyDetail> mPendingTeacher = new ArrayList<>();
    private ArrayList<NotificationReplyDetail> mPendingStudent = new ArrayList<>();

    public GetNotificationStatsData(String token, String n_id){
        mAccessToken = token; mNotificationId = n_id;
    }
    public String getNotificationId(){return mNotificationId;}
    public String getAccessToken(){return mAccessToken;}

    public void addReply(NotificationReplyDetail r){
        if (r.getUserType() == CommonDetails.USER_TYPE_TEACHER){
            if(r.getNotificationReply() == CommonDetails.NotificationReplyEnum.NOTIFICATION_REPLY_YES){
                mRepliedYesTeacher.add(r);
            }
            else if (r.getNotificationReply() == CommonDetails.NotificationReplyEnum.NOTIFICATION_REPLY_NO){
                mRepliedNoTeacher.add(r);
            }
            else{
                mPendingTeacher.add(r);
            }
        }
        else{
            if(r.getNotificationReply() == CommonDetails.NotificationReplyEnum.NOTIFICATION_REPLY_YES){
                mRepliedYesStudent.add(r);
            }
            else if (r.getNotificationReply() == CommonDetails.NotificationReplyEnum.NOTIFICATION_REPLY_NO){
                mRepliedNoStudent.add(r);
            }
            else{
                mPendingStudent.add(r);
            }
        }
    }

    public int getTotalNumberOfReplyByTeacher(){return (mRepliedNoTeacher.size() + mRepliedYesTeacher.size());}
    public int getNumberOfYesReplyByTeacher(){return mRepliedYesTeacher.size();}
    public int getNumberOfNoReplyByTeacher(){return mRepliedNoTeacher.size();}

    public int getTotalNumberOfReplyByStudent(){return (mRepliedNoStudent.size() + mRepliedYesStudent.size());}
    public int getNumberOfYesReplyByStudent(){return mRepliedYesStudent.size();}
    public int getNumberOfNoReplyByStudent(){return mRepliedNoStudent.size();}

    public int getTotalNumberOfPendingReply(){return (mPendingStudent.size() + mPendingTeacher.size());}
    public int getNumberOfPendingReplyByStudent(){return mPendingStudent.size();}
    public int getNumberOfPendingReplyByTeacher(){return mPendingTeacher.size();}

    public ArrayList<NotificationReplyDetail> getAllReplies(){
        ArrayList<NotificationReplyDetail> replies = new ArrayList<>();
        replies.addAll(mPendingStudent);
        replies.addAll(mPendingTeacher);
        replies.addAll(mRepliedYesTeacher);
        replies.addAll(mRepliedNoTeacher);
        replies.addAll(mRepliedYesStudent);
        replies.addAll(mRepliedNoStudent);
        return replies;
    }
    public ArrayList<NotificationReplyDetail> getAllYesReplies(){
        ArrayList<NotificationReplyDetail> replies = new ArrayList<>();
        replies.addAll(mRepliedYesTeacher);
        replies.addAll(mRepliedYesStudent);
        return replies;
    }
    public ArrayList<NotificationReplyDetail> getAllNoReplies(){
        ArrayList<NotificationReplyDetail> replies = new ArrayList<>();
        replies.addAll(mRepliedNoTeacher);
        replies.addAll(mRepliedNoStudent);
        return replies;
    }
    public ArrayList<NotificationReplyDetail> getAllPendingReplies(){
        ArrayList<NotificationReplyDetail> replies = new ArrayList<>();
        replies.addAll(mPendingStudent);
        replies.addAll(mPendingTeacher);
        return replies;
    }

    public ArrayList<NotificationReplyDetail> getYesByTeachers(){return mRepliedYesTeacher;}
    public ArrayList<NotificationReplyDetail> getNoByTeachers(){return mRepliedNoTeacher;}
    public ArrayList<NotificationReplyDetail> getPendingByTeachers(){return mPendingTeacher;}
    public ArrayList<NotificationReplyDetail> getAllRepliesByTeacher(){
        ArrayList<NotificationReplyDetail> replies = new ArrayList<>();
        replies.addAll(mPendingTeacher);
        replies.addAll(mRepliedYesTeacher);
        replies.addAll(mRepliedNoTeacher);
        return replies;
    }

    public ArrayList<NotificationReplyDetail> getYesByStudent(){return mRepliedYesStudent;}
    public ArrayList<NotificationReplyDetail> getNoByStudent(){return mRepliedNoStudent;}
    public ArrayList<NotificationReplyDetail> getPendingByStudent(){return mPendingStudent;}
    public ArrayList<NotificationReplyDetail> getAllRepliesByStudent(){
        ArrayList<NotificationReplyDetail> replies = new ArrayList<>();
        replies.addAll(mPendingStudent);
        replies.addAll(mRepliedYesStudent);
        replies.addAll(mRepliedNoStudent);
        return replies;
    }
}
