package com.example.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "icm_db";

	public DBHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// 社团表
		db.execSQL("create table clubs(" + "clubId text not null,"
				+ "clubName text not null)");

		// 用户表
		db.execSQL("create table users(" + "uid text primary key,"
				+ "userName text not null," + "userPassword text)");

		// 活动表
		db.execSQL("create table activities(" + "atyId text not null,"
				+ "atyName text not null," + "atyContent text,"
				+ "created_time timestamp," + "deadline datetime)");

		// 任务表
		db.execSQL("create table tasks(" + "taskId text not null,"
				+ "taskName text not null," + "taskContent text,"
				+ "created_time timestamp," + "deadline datetime)");

		/*
		 * 关系表
		 */
		db.execSQL("create table clubs_users(" + "clubId text not null,"
				+ "uid text not null)");

		db.execSQL("create table clubs_activities(" + "clubId text not null,"
				+ "atyId text not null)");

		db.execSQL("create table activities_users (" + "atyId text not null,"
				+ "uid text not null)");

		db.execSQL("create table activities_tasks(" + "atyId text not null,"
				+ "taskId text not null)");

		db.execSQL("create table tasks_users(" + "taskId text not null,"
				+ "uid text not null)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

	// -----------------------------------------------------------------------------------------------------------------
	// 对SQLite的操作（CRUD）
	/*
	 * 对clubs表的操作
	 */
	// 添加一个社团表
	public void addClub(Club club) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("clubId", club.getClubId());
		values.put("clubName", club.getClubName());
		db.insert("clubs", null, values);
		db.close();
	}

	// 获取某个社团的信息，通过某个clubId返回社团信息，没有找到则返回null
	public Club getClubByClubId(String clubId) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query("clubs", null, "clubId = ?",
				new String[] { clubId }, null, null, null);
		if (cursor.moveToFirst()) {
			System.out.println("找到该社团>>>>>>>>>>>>>>>>>>>>>>>>");
			Club club = new Club(cursor.getString(0), cursor.getString(1));
			return club;
		} else {
			System.out.println("没有找到该社团>>>>>>>>>>>>>>>>>>>>");
			return null;
		}
	}

	// 获取所有的社团信息
	public List<Club> getAllClub() {
		List<Club> clubList = new ArrayList<Club>();
		String selectQuery = "SELECT * FROM clubs";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Club club = new Club();
				club.setClubId(cursor.getString(0));
				club.setClubName(club.getClubName());
				clubList.add(club);
			} while (cursor.moveToNext());
		}
		return clubList;
	}

	// 更新某个社团的信息，给定某个社团信息，主要是clubId
	public int updateClub(Club club) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("clubId", club.getClubId());
		values.put("clubName", club.getClubName());
		return db.update("clubs", values, "clubId = ?",
				new String[] { club.getClubId() });
	}

	// 删除某个社团的信息，通过给定的clubId
	public void deleteClubByClubId(String clubId) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete("clubs", "clubId = ?", new String[] { clubId });
		db.close();
	}

	// 获取社团的个数
	public int getClubsCount() {
		String countQuery = "SELECT * FROM clubs";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int count = cursor.getCount();
		cursor.close();
		return count;
	}

	// ---------------------------------------------------------------------------------------------------------------------------

	/*
	 * 对users表的操作
	 */
	// 添加用户
	public void addUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("uid", user.getUid());
		values.put("userName", user.getUserName());
		values.put("userPassword", user.getUserPassword());
		db.insert("users", null, values);
		db.close();
	}

	// 获取某个用户信息，通过uid返回用户信息
	public User getUserByUid(String uid) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query("users", new String[] { "uid", "userName",
				"userPassword" }, "uid = ?", new String[] { uid }, null, null,
				null);
		if (cursor.moveToFirst()) {
			System.out.println("找到该用户>>>>>>>>>>>>>>>>>>>>>>>>>>");

			User user = new User(cursor.getString(0), cursor.getString(1),
					cursor.getString(2));
			return user;
		} else {
			System.out.println("没有找到该用户>>>>>>>>>>>>>>>>>>>>>>");
			return null;
		}

	}

	// 获取所有的用户信息，返回一个所有用户的列表
	public List<User> getAllUser() {
		List<User> userList = new ArrayList<User>();
		String selectQuery = "SELECT * FROM users";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				User user = new User();
				user.setUid(cursor.getString(0));
				user.setUserName(cursor.getString(1));
				user.setUserPassword(cursor.getString(2));
				userList.add(user);
			} while (cursor.moveToNext());
		}
		return userList;
	}

	// 更新某个用户的信息，给定某个用户信息，主要是uid
	public int updateUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("userName", user.getUserName());
		values.put("userPassword", user.getUserPassword());
		return db.update("users", values, "uid = ?",
				new String[] { user.getUid() });
	}

	// 删除某个用户的信息，通过给定的uid
	public void deleteUserByUid(String uid) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete("users", "uid = ?", new String[] { uid });
		db.close();
	}

	// 获得用户个数
	public int getUsersCount() {
		String countQuery = "SELECT * FROM users";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int count = cursor.getCount();
		cursor.close();
		return count;
	}

	// --------------------------------------------------------------------------------------------------------------------------
	/*
	 * 队活动表的操作
	 */
	// 添加一个活动
	public void addDAO(DAO aty) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("atyId", aty.getAtyId());
		values.put("atyName", aty.getAtyName());
		values.put("atyContent", aty.getAtyContent());
		values.put("deadline", aty.getDeadline());
		db.insert("activities", null, values);
		db.close();
	}

	// 获取某个活动的信息，通过atyId返回活动信息，没有则返回null
	public DAO getAtyByAtyId(String atyId) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query("activities", null, "atyId",
				new String[] { atyId }, null, null, null);
		if (cursor.moveToFirst()) {
			System.out.println("找到改活动信息" + atyId);
			DAO aty = new DAO(cursor.getColumnName(0), cursor.getString(1),
					cursor.getString(2), cursor.getString(3),
					cursor.getString(4));
			return aty;
		} else {
			System.out.println("没有找到该活动信息" + atyId);
			return null;
		}
	}

	// 获取所有的活动信息
	public List<DAO> getAllAty() {
		List<DAO> atyList = new ArrayList<DAO>();
		String selectQuery = "SELECT * FROM activities";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				DAO aty = new DAO();
				aty.setAtyId(cursor.getString(0));
				aty.setAtyName(cursor.getString(1));
				aty.setAtyContent(cursor.getString(2));
				aty.setCreated_time(cursor.getString(3));
				aty.setDeadline(cursor.getString(4));
				atyList.add(aty);
			} while (cursor.moveToNext());
		}
		return atyList;
	}

	// 更新某个活动的信息，给定某个活动信息，主要是atyId
	public int updateAty(DAO aty) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("atyId", aty.getAtyId());
		values.put("atyName", aty.getAtyName());
		values.put("atyContent", aty.getAtyContent());
		values.put("deadline", aty.getDeadline());
		return db.update("activities", values, "atyId = ?",
				new String[] { aty.getAtyId() });
	}

	// 删除某个活动信息，通过给定的atyId
	public void deleteAtyByAtyId(String atyId) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete("activities", "atyId = ?", new String[] { atyId });
		db.close();
	}

	// 获得活动的个数
	public int getAtyCount() {
		String countQuery = "SELECT * FROM ativities";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int count = cursor.getCount();
		cursor.close();
		return count;
	}

	// -----------------------------------------------------------------------------------------------------------
	/*
	 * 对任务表的操作
	 */
	public void addTask(Task task) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("taskId", task.getTaskId());
		values.put("taskName", task.getTaskName());
		values.put("taskContent", task.getTaskContent());
		values.put("dateline", task.getDeadline());
		db.insert("tasks", null, values);
		db.close();
	}

	// 获取某个任务的信息，通过某个taksId返回任务信息，没有则返回null
	public Task getTaskByTaskId(String taskId) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query("tasks", null, "taskId = ?",
				new String[] { taskId }, null, null, null);
		if (cursor.moveToFirst()) {
			System.out.println("找到该任务>>>>>>>>>>>>>>>>>>>>>" + taskId);
			Task task = new Task(cursor.getString(0), cursor.getString(1),
					cursor.getString(2), cursor.getString(3),
					cursor.getString(4));
			return task;
		} else {
			System.out.println("没有找到该任务>>>>>>>>>>>>>>>>>>" + taskId);
			return null;
		}
	}

	// 获取所有任务信息
	public List<Task> getAllTask() {
		List<Task> taskList = new ArrayList<Task>();
		String selectQuery = "SELECT * FROM tasks";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Task task = new Task();
				task.setTaskId(cursor.getString(0));
				task.setTaskName(cursor.getString(1));
				task.setTaskContent(cursor.getString(2));
				task.setCreated_time(cursor.getString(3));
				task.setDeadline(cursor.getString(4));
				taskList.add(task);
			} while (cursor.moveToNext());
		}
		return taskList;
	}

	// 更新某个任务的信息，给定某个任务的信息，主要是taskId
	public int updateTask(Task task) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("taskId", task.getTaskId());
		values.put("taskName", task.getTaskName());
		values.put("taskContent", task.getTaskContent());
		values.put("deadline", task.getDeadline());
		return db.update("tasks", values, "taskId = ?",
				new String[] { task.getTaskId() });
	}

	// 删除某个任务信息，通过给定的taskId
	public void deleteTaskByTaskId(String taskId) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete("tasks", "taskId = ?", new String[] { taskId });
		db.close();
	}

	// 获取任务总数
	public int getTasksCount() {
		String coutnQuery = "SELECT * FROM tasks";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(coutnQuery, null);
		int count = cursor.getCount();
		cursor.close();
		return count;
	}

	// *************************************************************************
	/*
	 * 关系表的操作
	 */
	/*
	 * 社团与用户的关系
	 */
	// 添加一组社团与用户关系
	public void addClub_User(String clubId, String uid) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("clubId", clubId);
		values.put("uid", uid);
		db.insert("clubs_users", null, values);
		db.close();
	}

	// 通过clubId找到所有用户数据
	public List<User> getAllUsersByClubId(String clubId) {
		List<User> userList = new ArrayList<User>();
		String selectQuery = "SELECT users.* FROM users, clubs_users "
				+ "WHERE clubId = ? AND users.uid = clubs_users.uid";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, new String[] { clubId });

		if (cursor.moveToFirst()) {
			do {
				User user = new User();
				user.setUid(cursor.getString(0));
				user.setUserName(cursor.getString(1));
				userList.add(user);
			} while (cursor.moveToNext());
		}
		return userList;
	}

	// 通过uid找到所有社团的信息
	public List<Club> getAllClubsByUid(String uid) {
		List<Club> clubList = new ArrayList<Club>();
		String selectQuery = "SELECT clubs.* FROM clubs, clubs_users "
				+ "WHERE uid = ? AND clubs.clubId = clubs_users.clubId";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, new String[] { uid });

		if (cursor.moveToFirst()) {
			do {
				Club club = new Club();
				club.setClubId(cursor.getString(0));
				club.setClubName(cursor.getString(1));
				clubList.add(club);
			} while (cursor.moveToNext());
		}
		return clubList;
	}

	// --------------------------------------------------------------------------------------------------------------------------------------
	/*
	 * 社团与用户的关系
	 */
	// 添加一组社团与活动关系
	public void addClub_DAO(String clubId, String atyId) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("clubId", clubId);
		values.put("atyId", atyId);
		db.insert("clubs_activities", null, values);
		db.close();
	}

	// 通过clubId找到所有活动数据
	public List<DAO> getAllAtyByClubId(String clubId) {
		List<DAO> atyList = new ArrayList<DAO>();
		String selectQuery = "SELECT activities.* FROM activities, clubs_activities "
				+ "WHERE clubId = ? AND activities.uid = clubs_activities.uid";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, new String[] { clubId });

		if (cursor.moveToFirst()) {
			do {
				DAO aty = new DAO();
				aty.setAtyId(cursor.getString(0));
				aty.setAtyName(cursor.getString(1));
				aty.setAtyContent(cursor.getString(2));
				aty.setCreated_time(cursor.getString(3));
				aty.setDeadline(cursor.getString(4));
				atyList.add(aty);
			} while (cursor.moveToNext());
		}
		return atyList;
	}

	// 通过atyId找到所有社团的信息
	public List<Club> getAllClubsByAtyId(String atyId) {
		List<Club> clubList = new ArrayList<Club>();
		String selectQuery = "SELECT clubs.* FROM clubs, clubs_activities "
				+ "WHERE atyId = ? AND clubs.clubId = clubs_activities.clubId";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, new String[] { atyId });

		if (cursor.moveToFirst()) {
			do {
				Club club = new Club();
				club.setClubId(cursor.getString(0));
				club.setClubName(cursor.getString(1));
				clubList.add(club);
			} while (cursor.moveToNext());
		}
		return clubList;
	}

	// --------------------------------------------------------------------------------------------------------------------------
	/*
	 * 活动和用户的关系
	 */
	// 添加一组活动和用户关系
	public void addAty_User(String atyId, String uid) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("atyId", atyId);
		values.put("uid", uid);
		db.insert("activities_users", null, values);
		db.close();
	}

	// 通过clubId找到所有用户数据
	public List<User> getAllUsersByAtyId(String atyId) {
		List<User> userList = new ArrayList<User>();
		String selectQuery = "SELECT users.* FROM users, activities_users "
				+ "WHERE atyId = ? AND users.uid = activities_users.uid";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, new String[] { atyId });

		if (cursor.moveToFirst()) {
			do {
				User user = new User();
				user.setUid(cursor.getString(0));
				user.setUserName(cursor.getString(1));
				userList.add(user);
			} while (cursor.moveToNext());
		}
		return userList;
	}

	// 通过uid找到所有活动的信息
	public List<DAO> getAllAtysByUid(String uid) {
		List<DAO> atyList = new ArrayList<DAO>();
		String selectQuery = "SELECT activities.* FROM activities, activities_users "
				+ "WHERE uid = ? AND activities.atyId = activities_users.atyId";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, new String[] { uid });

		if (cursor.moveToFirst()) {
			do {
				DAO aty = new DAO();
				aty.setAtyId(cursor.getString(0));
				aty.setAtyName(cursor.getString(1));
				aty.setAtyContent(cursor.getString(2));
				aty.setCreated_time(cursor.getString(3));
				aty.setDeadline(cursor.getString(4));
				atyList.add(aty);
			} while (cursor.moveToNext());
		}
		return atyList;
	}

	// -------------------------------------------------------------------------------------------------------------
	/*
	 * 活动与任务的关系
	 */
	// 添加一组活动与任务关系
	public void addAty_Task(String atyId, String taskId) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("atyId", atyId);
		values.put("taskId", taskId);
		db.insert("activities_tasks", null, values);
		db.close();
	}

	// 通过atyId找到所有任务数据
	public List<Task> getAllTaskByAtyId(String atyId) {
		List<Task> taskList = new ArrayList<Task>();
		String selectQuery = "SELECT tasks.* FROM tasks, activities_tasks "
				+ "WHERE atyId = ? AND tasks.taskId = activities_tasks.taskId";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, new String[] { atyId });

		if (cursor.moveToFirst()) {
			do {
				Task task = new Task();
				task.setTaskId(cursor.getString(0));
				task.setTaskName(cursor.getString(1));
				task.setTaskContent(cursor.getString(2));
				task.setCreated_time(cursor.getString(3));
				task.setDeadline(cursor.getString(4));
				taskList.add(task);
			} while (cursor.moveToNext());
		}
		return taskList;
	}

	// 通过taskId找到所有活动的信息
	public List<DAO> getAllAtysByTaskId(String taskId) {
		List<DAO> atyList = new ArrayList<DAO>();
		String selectQuery = "SELECT activities.* FROM activities, activities_tasks "
				+ "WHERE taskId = ? AND activities.atyId = activities_tasks.atyId";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, new String[] { taskId });

		if (cursor.moveToFirst()) {
			do {
				DAO aty = new DAO();
				aty.setAtyId(cursor.getString(0));
				aty.setAtyName(cursor.getString(1));
				aty.setAtyContent(cursor.getString(2));
				aty.setCreated_time(cursor.getString(3));
				aty.setDeadline(cursor.getString(4));
				atyList.add(aty);
			} while (cursor.moveToNext());
		}
		return atyList;
	}

	// ---------------------------------------------------------------------------------------------------
	/*
	 * 任务和用户的关系操作
	 */
	// 添加一组任务和用户关系
	public void addTask_User(String taskId, String userId) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("taskId", taskId);
		values.put("userId", userId);
		db.insert("tasks_users", null, values);
		db.close();
	}

	// 通过taskId找到所有用户数据
	public List<User> getAllUsersByTaskId(String taskId) {
		List<User> userList = new ArrayList<User>();
		String selectQuery = "SELECT users.* FROM users, tasks_users "
				+ "WHERE taskId = ? AND users.uid = tasks_users.uid";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, new String[] { taskId });

		if (cursor.moveToFirst()) {
			do {
				User user = new User();
				user.setUid(cursor.getString(0));
				user.setUserName(cursor.getString(1));
				user.setUserPassword(cursor.getString(2));
				userList.add(user);
			} while (cursor.moveToNext());
		}
		return userList;
	}

	// 通过uid找到所有任务的信息
	public List<Task> getAllTasksByUid(String uid) {
		List<Task> taskList = new ArrayList<Task>();
		String selectQuery = "SELECT tasks.* FROM tasks, tasks_users "
				+ "WHERE taskId = ? AND tasks.taskId = tasks_users.taskId";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, new String[] { uid });

		if (cursor.moveToFirst()) {
			do {
				Task task = new Task();
				task.setTaskId(cursor.getString(0));
				task.setTaskName(cursor.getString(1));
				task.setTaskContent(cursor.getString(2));
				task.setCreated_time(cursor.getString(3));
				task.setDeadline(cursor.getString(4));
				taskList.add(task);
			} while (cursor.moveToNext());
		}
		return taskList;
	}

}
