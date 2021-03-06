package com.blibli.pos_minimarket.Controller;

import com.blibli.pos_minimarket.Model.Employee;
import com.blibli.pos_minimarket.Services.ReportService;
import com.blibli.pos_minimarket.Services.TransactionDetailService;
import com.blibli.pos_minimarket.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ReportController {

    @Autowired
    private TransactionService transactionService = new TransactionService();

    @Autowired
    private TransactionDetailService transactionDetailService = new TransactionDetailService();

    @Autowired
    private ReportService reportService = new ReportService();

    @RequestMapping(value = "Report-Transaction")
    public String showReportTransaction(HttpServletRequest request,Model model, @ModelAttribute("searchKey") String searchKey) {
        Employee employee = (Employee) request.getSession().getAttribute("pegawai");
        model.addAttribute("pegawai", employee);
        if (employee == null || employee.getRole().getName().equals("Kasir")) {
            return "Login";
        }
        model.addAttribute("transactionList",transactionService.showAllTransaction());
        if(!searchKey.equals("")) {
            model.addAttribute("transactionDetailList", transactionDetailService.showOne(Integer.parseInt(searchKey)));
        }return "Report-Transaction";
    }

    @RequestMapping(value = "/report-transaction/detail/{transactionId}", method = RequestMethod.GET)
    public String detailCategory(HttpServletRequest request,@PathVariable Integer transactionId, Model model){
        Employee employee = (Employee) request.getSession().getAttribute("pegawai");
        model.addAttribute("pegawai", employee);
        if (employee == null || employee.getRole().getName().equals("Kasir")) {
            return "Login";
        }
        model.addAttribute("transactionDetailList", transactionDetailService.showOne(transactionId));
        return "Report-TransactionDetail";
    }

    @RequestMapping(value = "Report-Statistics")
    public String showReportStatistics(HttpServletRequest request,Model model) {
        Employee employee = (Employee) request.getSession().getAttribute("pegawai");
        model.addAttribute("pegawai", employee);
        if (employee == null || employee.getRole().getName().equals("Kasir")) {
            return "Login";
        }
        model.addAttribute("reports",reportService.getAll());
        return "Report-Statistics";
    }

    @RequestMapping(value = "/report-transaction/detail",params = "cancel", method = RequestMethod.POST)
    public String cancelReport(){
        return "redirect:/Report-Transaction";
    }

//    @RequestMapping(value = "Report-Transaction")
//    public String showReportTransaction(Model model) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        List<TransactionDetail> transactionDetailList = new ArrayList<>();
//        try{
//        transactionDetailList =  transactionDetailService.showOne(27);
//        String json = objectMapper.writeValueAsString(transactionDetailList);
//            model.addAttribute("transactionList",json);}
//        catch (Exception EX){
//            System.out.println("error show reportTransaction" + EX.toString());
//        }
////        model.addAttribute("transactionList", transactionService.showAllTransaction());
//
//        return "Report-Transaction";
//
//    }

//    @RequestMapping(value = "Report-Transaction/Detail")
//    public ModelAndView showDetailTransaction(@ModelAttribute("searchKey") Integer searchKey){
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("transactionDetailList",transactionDetailService.showOne(searchKey));
//        mav.setViewName("Report-Transaction");
//        return mav;
//    }
}
