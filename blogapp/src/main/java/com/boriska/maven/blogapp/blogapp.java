/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boriska.maven.blogapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author bk
 */
public class blogapp extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet blogapp</title>"); 
            out.println("<style>");
            out.println("button {\n" +
            "    background-color: lightskyblue;\n" +
            "    border-radius: 8px;\n" +
            "    border-width: 1px;\n" +
            "    margin: 6px;\n" +
            "    padding: 10px 32px;\n" +
            "    font-size: 16px;\n" +
            "}\n" +
            "\n" +
            ".bigButton {\n" +
            "    padding:16px 32px;\n" +
            "}\n" +
            "\n" +
            ".post {\n" +
            "    border-style: groove;\n" +
            "    border-width: 2px;\n" +
            "    margin: 6px;\n" +
            "}\n" +
            "\n" +
            ".title {\n" +
            "    padding-left: 16px;\n" +
            "    padding-top: 16px;\n" +
            "    padding-bottom: 6px;\n" +
            "}\n" +
            "\n" +
            ".date {\n" +
            "    padding-left: 16px;\n" +
            "    padding-top: 6px;\n" +
            "}\n" +
            "\n" +
            ".postButtons {\n" +
            "    margin-top: -12px;\n" +
            "    text-align: right;\n" +
            "}\n" +
            "\n" +
            ".addButtons {\n" +
            "    text-align: right;\n" +
            "    margin-bottom: 16px\n" +
            "}\n" +
            "\n" +
            "textarea {\n" +
            "    align-self: center;\n" +
            "}");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<button class=\"bigButton\" id='addNewPostButton' onclick='addNewPostPressed()'>add new post</button>");
            DBWorker dbWorker = (DBWorker) getServletContext().getAttribute("DBWorker");
            
            try {
                List<Post> posts = dbWorker.listOfPosts();
                for(Post post : posts) {
                    out.println("<div class=\"post\" id='"+post.getTimestamp()+"'>");
                    out.println("<div class=\"title\">"+post.getTitle()+"</div>");
                    String date = new SimpleDateFormat("d.M.yyyy").format(post.getTimestamp());
                    out.println("<div class=\"date\">"+date+"</div>");
                    out.println("<div class=\"postButtons\">");
                    out.println("<button onclick='viewPostFunction(\""+post.getTitle()+"\",\""+post.getText()+"\")'>view</button>");
                    out.println("<button onclick='deletePostFunction("+post.getTimestamp()+")'>delete</button>");
                    out.println("</div>");
                    out.println("</div>");
                }
                if(posts.size() == 0) {
                    out.println("<h3>There are no posts yet</h3>");
                }
            }
            catch (Exception e) {
                System.out.println(e.toString());
            }
            out.println("<dialog id='addPost'>");
            out.println("<form method='dialog'>");
            out.println("<p><label>title</label></p>");
            out.println("<textarea id='addPostTitle' cols='80' rows='2' style='resize: none;'></textarea>");
            out.println("<p><label>text</label></p>");
            out.println("<textarea id='addPostText' cols='80' rows='15' style='resize: none;'></textarea>");
            out.println("<p class=\"addButtons\">");
            out.println("<button class=\"bigButton\">cancel</button>");
            out.println("<button class=\"bigButton\" id='savePostButton' onclick='addNewPostEvent()'>save post</button>");
            out.println("</p>");
            out.println("</form>");
            out.println("</dialog>");
            
            out.println("<script type=\"text/javascript\">");
            out.println("function addNewPostPressed() {\n" +
                "    var savePostButton = document.getElementById('savePostButton')\n" +
                "    savePostButton.hidden = false\n" +
                "    var addPostTitle = document.getElementById('addPostTitle')\n" +
                "    addPostTitle.value = ''\n" +
                "    var addPostText = document.getElementById('addPostText')\n" +
                "    addPostText.value = ''\n" +
                "    var addPostDialog = document.getElementById('addPost')\n" +
                "    addPostDialog.showModal()\n" +
                "} \n" +
                "             \n" +
                "function viewPostFunction(title, text) {\n" +
                "    console.log(title, text)\n" +
                "    var savePostButton = document.getElementById('savePostButton')\n" +
                "    savePostButton.hidden = true\n" +
                "    var addPostTitle = document.getElementById('addPostTitle')\n" +
                "    addPostTitle.value = title\n" +
                "    var addPostText = document.getElementById('addPostText')\n" +
                "    addPostText.value = text\n" +
                "    var addPostDialog = document.getElementById('addPost')\n" +
                "    addPostDialog.showModal()\n" +
                "}\n" +
                "            \n" +
                "function addNewPostEvent() {\n" +
                "    console.log('Trying to add things')\n" +
                "    var addPostTitle = document.getElementById('addPostTitle')\n" +
                "    console.log(addPostTitle.value)\n" +
                "    var addPostText = document.getElementById('addPostText')\n" +
                "    console.log(addPostText.value)\n" +
                "    var xhttp = new XMLHttpRequest()\n" +
                "    xhttp.open('POST', '/', true)\n" +
                "    xhttp.setRequestHeader(\"Content-type\", \"application/x-www-form-urlencoded\")\n" +
                "    xhttp.onload = function() { window.location.reload() }\n" +
                "    xhttp.send(\"title=\"+ escape(addPostTitle.value) + \"&text=\" +escape(addPostText.value))\n" +
                "}\n" +
                "            \n" +
                "function deletePostFunction(timestamp) {\n" +
                "    console.log(timestamp)\n" +
                "    var xhttp = new XMLHttpRequest()\n" +
                "    xhttp.open('POST', '/', true)\n" +
                "    xhttp.setRequestHeader(\"Content-type\", \"application/x-www-form-urlencoded\")\n" +
                "    xhttp.onload = function() { window.location.reload() }\n" +
                "    xhttp.send(\"timestamp=\"+ timestamp)\n" +
                "}");
            out.println("</script>");

            out.println("</body>");
            out.println("</html>");
        }
        catch (Exception e) {
            System.out.println(e.toString());
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
        processRequest(request, response);
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
        DBWorker dbWorker = (DBWorker) getServletContext().getAttribute("DBWorker");
        
        if (request.getParameter("timestamp") != null) {
            dbWorker.removePost(Long.parseLong(request.getParameter("timestamp")));
        }
        else if (request.getParameter("title") != null && request.getParameter("text") != null) {
            dbWorker.addPost(request.getParameter("title"), request.getParameter("text"));
        }
        else {
            System.out.println("Interesting POST came. No idea what to do with it. So I will just ignore it");
        }
        
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
