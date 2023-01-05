package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.Currency;
import model.ExchangeRate;
import model.Money;
import persistence.rest.ExchangeRateLoaderFromWebService;
import view.swing.DialogSwing;
import view.swing.DisplaySwing;

public class MCController implements ActionListener {
    private final DialogSwing dialogSwing;
    private final ExchangeRateLoaderFromWebService exchangeRateLoaderFromWebService;

    public MCController(DialogSwing dialogSwing,
                        ExchangeRateLoaderFromWebService exchangeRateLoaderFromWebService) {
        this.dialogSwing = dialogSwing;
        this.exchangeRateLoaderFromWebService = exchangeRateLoaderFromWebService;
        this.dialogSwing.registerController(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
       Money money = this.dialogSwing.getMoney();
       
       if(money.getAmount() < 0){
           JOptionPane.showMessageDialog(dialogSwing, "Debe introducir un número aceptable", "ERROR", JOptionPane.ERROR_MESSAGE);
       } else if (money.getAmount() == 0){
                             
       } else {
           Currency currencyFrom = money.getCurrency();
           Currency currencyTo = this.dialogSwing.getCurrencyTo();
           ExchangeRate exchangeRate = this.exchangeRateLoaderFromWebService.exchangerateLoader(currencyFrom, currencyTo);
       
           this.dialogSwing.refreshMoney(new Money(exchangeRate.getRate() * money.getAmount(), currencyTo));
       }
    } 
}
