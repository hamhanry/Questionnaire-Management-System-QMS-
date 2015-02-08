/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import qms.database.connection;

/**
 *
 * @author admin
 */
public class updateDBServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {           
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        
         PrintWriter out = response.getWriter();
         String query = "";
         
         String campaignName = request.getParameter("campaignInput");
         String startDate = request.getParameter("startDateInput");
         String endDate = request.getParameter("endDateInput");
         
         String questionTitle = request.getParameter("questionnaireInput");
         String questionDescription = request.getParameter("questionnaireDescInput");
         
         String sectionNo = request.getParameter("sectionCounterInput");
         String sectionTitle = request.getParameter("sectionHeaderTitle");
         String sectionText = request.getParameter("sectionHeaderText");
         
         String questionCounter = request.getParameter("questionCounter");
         
         String questionNo = request.getParameter("textCounterInput");
         String questionText = request.getParameter("questionTextInput");
         String questionDesc = request.getParameter("questionDescInput");;
         
         //System.out.println(id + ":" + name + ":" + lastname);
         System.out.println("campaign name : "+campaignName);
         System.out.println("Start date : "+ startDate);
         System.out.println("End date : "+ endDate);
         System.out.println("questionTitle : "+questionTitle);
         System.out.println("questionDescription : "+questionDescription);
         System.out.println("sectionNo : "+sectionNo);
         System.out.println("sectionTitle : "+sectionTitle);
         System.out.println("sectionText : "+sectionText);
         System.out.println("questionNo : "+questionNo);
         System.out.println("questionText : "+questionText);
         System.out.println("questionDesc : "+questionDesc);
         
         
         connection con = new connection();
         con.connect();
         //query =  "insert into campaign(ID) select max(id) + 1 from campaign";   
         
         //INSERT CAMPAIGN NAME
         query =  "insert into campaign(Name, startDate, endDate) values('"+campaignName+"', '"+startDate+"', '"+endDate+"')";         
         con.execUpdate(query);     
         
         //INSERT QUESTIONNAIRE TITLE & DESCRIPTION
         query="select a.ID from campaign a where a.Name='"+campaignName+"'";
         try {
            ResultSet rs = con.execQuery(query);            
            if(rs.next()){
                query = "insert into questionnaire(CampaignID,Title, Text) values("+Integer.parseInt(rs.getString(1))+",'"+questionTitle+"','"+questionDescription+"')";
                con.execUpdate(query);
            }else{
                out.print("wrong");
            }
        } catch (SQLException ex) {
            //Logger.getLogger(scanData.class.getName()).log(Level.SEVERE, null, ex);
        }    
         
         //INSERT QUESTIONNAIRE TITLE & DESCRIPTION
         query="select a.ID from questionnaire a where a.Title='"+questionTitle+"'";
         try {
            ResultSet rs = con.execQuery(query);            
            if(rs.next()){
                query = "insert into section(QuestionnaireID,Title, Text) values("+Integer.parseInt(rs.getString(1))+",'"+sectionTitle+"','"+sectionText+"')";
                con.execUpdate(query);
            }else{
                out.print("wrong");
            }
        } catch (SQLException ex) {
            //Logger.getLogger(scanData.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        //INSERT QUESTION
        query="select a.ID from section a where a.Title='"+questionTitle+"'";
         try {
            ResultSet rs = con.execQuery(query);            
            if(rs.next()){
                query = "insert into section(QuestionnaireID,Title, Text) values("+Integer.parseInt(rs.getString(1))+",'"+sectionTitle+"','"+sectionText+"')";
                con.execUpdate(query);
            }else{
                out.print("wrong");
            }
        } catch (SQLException ex) {
            //Logger.getLogger(scanData.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        /*//SHOW QUERY
         query =  "select * from campaign";
         ResultSet rs = con.execQuery(query);
         try {
            while(rs.next()){
                System.out.println(rs.getString("id") + " : " + rs.getString("name"));
            }
         } catch (SQLException ex) {
            Logger.getLogger(updateDBServlet.class.getName()).log(Level.SEVERE, null, ex);
        }*/
         
         con.closeConnect();
         
         out.print("success");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
