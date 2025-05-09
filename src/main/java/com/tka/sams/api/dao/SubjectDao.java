package com.tka.sams.api.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tka.sams.api.entity.Subject;

@Repository
public class SubjectDao {
	@Autowired
	private SessionFactory factory;

	public Subject getSubjectById(long subjectId) {
		Session session = null;
		Subject subject = null;
		try {
			session = factory.openSession();
			subject = session.get(Subject.class, subjectId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return subject;
	}

	public List<Subject> getAllSubjects() {
		Session session = null;
		List<Subject> list = null;
		try {
			session = factory.openSession();

			Criteria criteria = session.createCriteria(Subject.class);
			list = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

	public Subject createSubject(Subject subject) {
		Session session = null;
		Subject sub = null;
		try {
			session = factory.openSession();
			Transaction transaction = session.beginTransaction();

			Criteria criteria = session.createCriteria(Subject.class);
			criteria.add(Restrictions.eq("name", subject.getName()));
			List<Subject> list = criteria.list();
			if (list.isEmpty()) {
				session.save(subject);
				transaction.commit();
				sub = subject;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return sub;
	}
	
	public Subject updateSubject(Subject subjectDetails) {
	    Session session = null;

	    try {
	        session = factory.openSession();
	        Subject sub = session.get(Subject.class, subjectDetails.getId());

	        if (sub == null) {
	            return null; // or throw exception if subject doesn't exist
	        }

	        Transaction transaction = session.beginTransaction();
	        sub.setName(subjectDetails.getName()); // update fields
	        transaction.commit();
	        return sub;

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        if (session != null) session.close();
	    }
	    return null;
	}

//
//	public Subject updateSubject(Subject subjectDetails) {
//		Session session = null;
//		
//		try {
//			session = factory.openSession();
//			Subject sub=session.get(Subject.class, subjectDetails.getId());
//			System.out.println(sub);
//			Transaction transaction = session.beginTransaction();
//			sub.setName(subjectDetails.getName());
//			
//			session.update(subjectDetails);
//			transaction.commit();
//			return sub;
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			session.close();
//		}
//		return null;
//	}

	public String deleteSubject(long id) {
		Session session = null;
		String msg = null;
		try {
			session = factory.openSession();
			Subject subject = session.get(Subject.class, id);
			session.delete(subject);
			session.beginTransaction().commit();
			msg = "deleted";

		} catch (Exception e) {
			msg = null;
			e.printStackTrace();
		} finally {
			session.close();
		}
		return msg;
	}
}
