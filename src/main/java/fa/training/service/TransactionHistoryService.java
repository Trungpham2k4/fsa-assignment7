package fa.training.service;

import fa.training.dao.TransactionHistoryDAO;
import fa.training.dao.impl.TransactionHistoryDAOImpl;
import fa.training.entity.TransactionHistory;

import java.util.List;

public class TransactionHistoryService {
    private final TransactionHistoryDAO transactionHistoryDAO = new TransactionHistoryDAOImpl();
    public void add(TransactionHistory transactionHistory) {
        transactionHistoryDAO.save(transactionHistory);
    }
    public List<TransactionHistory> getAll() {
        return transactionHistoryDAO.findAll();
    }
    public TransactionHistory getById(int id) {
        return transactionHistoryDAO.findById(id);
    }

    public List<TransactionHistory> getTransactionHistoryByUserId(int userId) {
        return transactionHistoryDAO.findByUserId(userId);
    }

    public boolean update(TransactionHistory transactionHistory) {
        TransactionHistory history = transactionHistoryDAO.findById(transactionHistory.getTransactionId());
        if (history != null) {
            history.setNote(transactionHistory.getNote());
            transactionHistoryDAO.update(history);
            return true;
        }
        return false;
    }
    public void delete(int id) {
        transactionHistoryDAO.delete(id);
    }
}
