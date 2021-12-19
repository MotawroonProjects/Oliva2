package com.oliva2.models;

import java.io.Serializable;

public class ClosingDataModel extends StatusResponse implements Serializable {
    private Data data;

    public Data getData() {
        return data;
    }

    public class Data implements Serializable {
        private double cash_in_hand;
        private double total_sale_amount;
        private double total_payment;
        private double cash_payment;
        private double credit_card_payment;
        private double gift_card_payment;
        private double deposit_payment;
        private double cheque_payment;
        private double paypal_payment;
        private double total_sale_return;
        private double total_expense;
        private double total_cash;
        private int id;

        public double getCash_in_hand() {
            return cash_in_hand;
        }

        public double getTotal_sale_amount() {
            return total_sale_amount;
        }

        public double getTotal_payment() {
            return total_payment;
        }

        public double getCash_payment() {
            return cash_payment;
        }

        public double getCredit_card_payment() {
            return credit_card_payment;
        }

        public double getGift_card_payment() {
            return gift_card_payment;
        }

        public double getDeposit_payment() {
            return deposit_payment;
        }

        public double getCheque_payment() {
            return cheque_payment;
        }

        public double getPaypal_payment() {
            return paypal_payment;
        }

        public double getTotal_sale_return() {
            return total_sale_return;
        }

        public double getTotal_expense() {
            return total_expense;
        }

        public double getTotal_cash() {
            return total_cash;
        }

        public int getId() {
            return id;
        }
    }
}

