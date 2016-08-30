package edu.pima.cascade.authzTaglib;

import com.hannonhill.cascade.model.dom.Group;
import com.hannonhill.cascade.model.service.GroupService;
import com.hannonhill.cascade.model.service.ServiceProviderHolderBean;
import com.hannonhill.cascade.view.beans.security.LoginInformationBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequireGroupTag extends SimpleTagSupport {
	private String group;
	private String failUrl;
	
	private static Logger log;
	
	static
	{
		log = LoggerFactory.getLogger(RequireGroupTag.class);
	}
	
	////////////////////////////////////////////////////
	@Override
	public void doTag() throws JspException, IOException
	{
		log.debug("RequireGroupTag input group is: " + group);
		group = group.replaceAll("\\W+", ",");
		log.debug("RequireGroupTag cleaned group is: " + group);
		ArrayList<String> groups = new ArrayList<String>();
		groups.addAll(Arrays.asList(group.split(",")));
		if (log.isDebugEnabled())
		{
			String msg = "RequireGroupTag parsed group(s) as:\n";
			for (int i = 0; i < groups.size(); i++)
			{
				msg += groups.get(i) + "\n";
			}
			log.debug(msg);
		}
		
		 PageContext ctx  = (PageContext)getJspContext();
		 HttpSession sess = ctx.getSession();
		 LoginInformationBean login = (LoginInformationBean)sess.getAttribute("user");
		 
					 
		 GroupService gs = ServiceProviderHolderBean.getServiceProvider().getGroupService();
		 List<Group> cmsGroups = gs.getGroupsForUser(login.getUsername());
		 	 
		 
		 // if user U has any 1 of the group(s) specified in the group attr, then that is good enough
		 for (int i = 0; i < cmsGroups.size(); i++)
		 {
			 String cmsGroupName = cmsGroups.get(i).getName();
			 if (groups.contains(cmsGroupName)) 
			 {
				 // matched, we're done here
				 return;
			 }
		 }
		 
		 		 
		 // so if we get here, none of the groups matched.  do the redirect.
		 HttpServletResponse resp = (HttpServletResponse)ctx.getResponse();
		 resp.sendRedirect(failUrl);
	}
	
	//////////////////////////////////////////////////////

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getFailUrl() {
		return failUrl;
	}

	public void setFailUrl(String failUrl) {
		this.failUrl = failUrl;
	}
	
	
}