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
         
         int sectionNo =Integer.parseInt(request.getParameter("sectionCounterInput"));         
         String sectionTitle = request.getParameter("sectionHeaderTitle");
         String [] tmpSectionTitle = sectionTitle.split(",");
         String sectionText = request.getParameter("sectionHeaderText");
         String [] tmpSectionText = sectionText.split(",");
         
         //For count how many questions in the campaign
         int questionCounter = Integer.parseInt(request.getParameter("questionCounter"));
         String questionType = request.getParameter("questionTypeInput");
         //System.out.println("qustionType : " + request.getParameter("questionTypeInput")); 
         String [] tmpQuestionType = questionType.split(",");
         
         //TEXT
         int textHelp=0;
         int questionNo = Integer.parseInt(request.getParameter("textCounterInput"));
         String questionText = request.getParameter("questionTextInput");
         String [] tmpQuestionText = questionText.split(",");
         String questionDesc = request.getParameter("questionDescInput");;
         String [] tmpQuestionDesc = questionDesc.split(",");
         
         //PARAGRAPH
         int paraHelp=0;
         System.out.println("PARAGRAPH NO : "+request.getParameter("paraCounter"));
         int paragraphNo = Integer.parseInt(request.getParameter("paraCounter"));
         String paragraphText = request.getParameter("paragraphQuestionInput");
         String [] tmpParagraphText = paragraphText.split(",");
         String paragraphDesc = request.getParameter("paragraphDescInput");;
         String [] tmpParagraphDesc = paragraphDesc.split(",");
         
         System.out.println("campaign name : "+campaignName);
         System.out.println("Start date : "+ startDate);
         System.out.println("End date : "+ endDate);
         
         for(int i=0; i<sectionNo; i++){
             System.out.println("sectionTitle"+i+" : "+tmpSectionTitle[i]);
             System.out.println("sectionText : "+tmpSectionText[i]);
         }
         
         System.out.println("questionNo : "+questionNo);
         
         for(int i=0; i<questionNo; i++){
             System.out.println("questionText :"+i+" :  "+tmpQuestionText[i]);
             System.out.println("questionDesc :"+i+" :  "+tmpQuestionDesc[i]);
         }
         
         for(int i=0; i<paragraphNo; i++){
             System.out.println("paragraphText :"+i+" :  "+tmpParagraphText[i]);
             System.out.println("paragraphDesc :"+i+" :  "+tmpParagraphDesc[i]);
         }
         
         
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
         
        //INSERT SECTION TITLE & DESCRIPTION
        query="select a.ID from questionnaire a where a.Title='"+questionTitle+"'";
        try {
           ResultSet rs = con.execQuery(query);            
           if(rs.next()){
               for(int i =0; i<sectionNo; i++){
                    query = "insert into section(QuestionnaireID,Title, Text) values("+Integer.parseInt(rs.getString(1))+",'"+tmpSectionTitle[i]+"','"+tmpSectionText[i]+"')";
                    con.execUpdate(query);
               }
           }else{
               out.print("wrong");
           }
       } catch (SQLException ex) {
           //Logger.getLogger(scanData.class.getName()).log(Level.SEVERE, null, ex);
       }
         
        
        //--START FROM THIS ONE 
        //INSERT QUESTION
        System.out.println("adhaskdaskdksahdks : "+questionCounter);
        for(int i=0;i<sectionNo;i++){
            query="select ID from section "
                    + "where Title='"+tmpSectionTitle[i]+"'";
            try {
                ResultSet rs = con.execQuery(query); 
                paraHelp=0;
                textHelp=0;
                if(rs.next()){
                    for(int j=0;j<questionCounter;j++){
                        if(Integer.parseInt(tmpQuestionType[j])==1){//TEXT
                            query = "insert into question(QuestionTypeID,SectionID,Text)"
                                    + "values("+Integer.parseInt(tmpQuestionType[j])+", "+Integer.parseInt(rs.getString(1))+", "
                                    + "'"+tmpQuestionText[textHelp]+"')";
                            textHelp++;
                        //}else if(Integer.parseInt(tmpQuestionType[j])==18){//GRID
                        //}else if(Integer.parseInt(tmpQuestionType[j])==5){//MULTIPLE CHOICE
                        }else if(Integer.parseInt(tmpQuestionType[j])==12){//PARAGRAPH
                            System.out.println(tmpParagraphText[paraHelp]);
                            query = "insert into question(QuestionTypeID,SectionID,Text)"
                                    + "values("+Integer.parseInt(tmpQuestionType[j])+", "+Integer.parseInt(rs.getString(1))+", "
                                    + "'"+tmpParagraphText[paraHelp]+"')";
                            paraHelp++;
                            
                        }else{
                            
                        }
                        con.execUpdate(query);                        
                    }
                }else{
                    out.print("wrong");
                }
            } catch (SQLException ex) {
                //Logger.getLogger(scanData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }             
                
        //SHOW QUERY
        // query =  "select * from campaign";
        // ResultSet rs = con.execQuery(query);
        // try {
        //    while(rs.next()){
        //        System.out.println(rs.getString("id") + " : " + rs.getString("name"));
        //    }
        // } catch (SQLException ex) {
        //    Logger.getLogger(updateDBServlet.class.getName()).log(Level.SEVERE, null, ex);
        //}
         
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
