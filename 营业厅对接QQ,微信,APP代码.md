##营业厅对接QQ,微信,APP代码

```java

//传cookie:iPlanetDirectoryPro
//传url参数：type=3（3：APP）
public ActionResult HGLogin(string url, string type)
        {
            Organization org = organizationService.Find(o => o.oId == orgId);
            if (org.oName != "华南理工大学")
            {
                return Content("<script>alert('当前组织不支持！');</script>");
            }
            if (string.IsNullOrEmpty(type))
            {
                type = "0";
            }
            else
            {
                if (type != "0" && type != "3")
                {
                    return Content("<script>alert('入口类型不在范围内！');</script>");
                }
            }
            if (string.IsNullOrEmpty(url))
            {
                url = "Index";
            }
            Idstar.CIdentityManagerClass im = new Idstar.CIdentityManagerClass();
            string loginUrl = (im.GetLoginURL() + "?goto=" + System.Configuration.ConfigurationManager.AppSettings["DTBSUrl"] + "/Home/HGLogin" + "?url=" + url).Replace(System.Configuration.ConfigurationManager.AppSettings["TYRZIP"], System.Configuration.ConfigurationManager.AppSettings["TYRZYM"]);
            string logoutURL = (im.GetLogoutURL() + "?goto=" + System.Configuration.ConfigurationManager.AppSettings["DTBSUrl"] + "/Home/Index").Replace(System.Configuration.ConfigurationManager.AppSettings["TYRZIP"], System.Configuration.ConfigurationManager.AppSettings["TYRZYM"]);
            if (System.Web.HttpContext.Current.Request.Cookies["iPlanetDirectoryPro"] == null)
            {
                im.Quit();
                return Redirect(loginUrl);
            }
            else
            {
                if (string.IsNullOrEmpty(System.Web.HttpContext.Current.Request.Cookies["iPlanetDirectoryPro"].Value))
                {
                    im.Quit();
                    return Redirect(loginUrl);
                }
                else
                {
                    string loginName = im.GetCurrentUser(System.Web.HttpUtility.UrlDecode(System.Web.HttpContext.Current.Request.Cookies["iPlanetDirectoryPro"].Value));
                    if (!string.IsNullOrEmpty(loginName))
                    {
                        object[] memberOf = (object[])im.GetUserAttribute_ext(loginName, "memberOf");

                        string memberOfs = "";
                        foreach (var item in memberOf)
                        {
                            memberOfs += item + "#";
                        }
                        //cn=JZG 是教职工；
                        //cn=HTG 是合同工；
                        //cn=BZKS 是本专科生；
                        //cn=YJS 研究生
                        //userType用户类型，0：不允许登录，1：教职工，2：学生
                        int userType = 0;
                        if (memberOfs.Contains("cn=JZG") || memberOfs.Contains("cn=HTG"))
                        {
                            userType = 1;
                        }
                        else if (memberOfs.Contains("cn=BZKS") || memberOfs.Contains("cn=YJS"))
                        {
                            userType = 2;
                        }
                        if (userType == 0)
                        {
                            if (System.Web.HttpContext.Current.Request.Cookies["iPlanetDirectoryPro"] != null)
                            {
                                im.DestroySSOToken(System.Web.HttpContext.Current.Request.Cookies["iPlanetDirectoryPro"].Value);
                                System.Web.HttpContext.Current.Request.Cookies.Remove("iPlanetDirectoryPro");
                            }
                            im.Quit();
                            if (type == "0")
                            {
                                return Content("<script>alert('该用户所在的统一认证组别不允许登录，请到信息服务大厅咨询！');window.location='" + logoutURL + "'</script>");
                            }
                            else
                            {
                                return Content("<script>alert('该用户所在的统一认证组别不允许登录，请到信息服务大厅咨询！');</script>");
                            }
                        }
                        else
                        {
                            if (userType == 1)
                            {
                                string loginName1 = loginName;
                                string loginName2 = loginName1 + "*";
                                int count = userInfoService.Count(d => (d.uLoginName == loginName1 || d.uLoginName == loginName2) && d.uFrom == 2 && d.uOrgId == orgId && d.uFlagDel == 0);
                                if (count > 0)
                                {
                                    if (count == 1)
                                    {
                                        var user = userInfoService.Find(d => (d.uLoginName == loginName1 || d.uLoginName == loginName2) && d.uFrom == 2 && d.uOrgId == orgId && d.uFlagDel == 0);

                                        uLoginName = user.uLoginName;
                                        accessType = type;
                                        string opName = "用户登陆";
                                        string describe = "登陆成功，用户帐号：" + uLoginName;
                                        DTSM.BusinessHallMobile.Extensions.PubMethodExtensions.AddLog(user, opName, describe, accessType, this);
                                        im.Quit();
                                        return Redirect(url);
                                    }
                                    else
                                    {
                                        if (System.Web.HttpContext.Current.Request.Cookies["iPlanetDirectoryPro"] != null)
                                        {
                                            im.DestroySSOToken(System.Web.HttpContext.Current.Request.Cookies["iPlanetDirectoryPro"].Value);
                                            System.Web.HttpContext.Current.Request.Cookies.Remove("iPlanetDirectoryPro");
                                        }
                                        im.Quit();
                                        if (type == "0")
                                        {
                                            return Content("<script>alert('该统一认证帐号对应多个教工网络账号，请到信息服务大厅咨询！');window.location='" + logoutURL + "'</script>");
                                        }
                                        else
                                        {
                                            return Content("<script>alert('该统一认证帐号对应多个教工网络账号，请到信息服务大厅咨询！');</script>");
                                        }
                                    }
                                }
                                else
                                {
                                    if (System.Web.HttpContext.Current.Request.Cookies["iPlanetDirectoryPro"] != null)
                                    {
                                        im.DestroySSOToken(System.Web.HttpContext.Current.Request.Cookies["iPlanetDirectoryPro"].Value);
                                        System.Web.HttpContext.Current.Request.Cookies.Remove("iPlanetDirectoryPro");
                                    }
                                    im.Quit();
                                    if (type == "0")
                                    {
                                        return Content("<script>alert('该统一认证帐号对应的教工网络账号不存在，请到信息服务大厅咨询！');window.location='" + logoutURL + "'</script>");
                                    }
                                    else
                                    {
                                        return Content("<script>alert('该统一认证帐号对应的教工网络账号不存在，请到信息服务大厅咨询！');</script>");
                                    }
                                }
                            }
                            else
                            {
                                object[] eduPersonStudentID = (object[])im.GetUserAttribute_ext(loginName, "eduPersonStudentID");
                                if (string.IsNullOrEmpty(eduPersonStudentID[0].ToString()))
                                {
                                    if (System.Web.HttpContext.Current.Request.Cookies["iPlanetDirectoryPro"] != null)
                                    {
                                        im.DestroySSOToken(System.Web.HttpContext.Current.Request.Cookies["iPlanetDirectoryPro"].Value);
                                        System.Web.HttpContext.Current.Request.Cookies.Remove("iPlanetDirectoryPro");
                                    }
                                    im.Quit();
                                    if (type == "0")
                                    {
                                        return Content("<script>alert('该统一认证帐号对应的学号为空，请到信息服务大厅咨询！');window.location='" + logoutURL + "'</script>");
                                    }
                                    else
                                    {
                                        return Content("<script>alert('该统一认证帐号对应的学号为空，请到信息服务大厅咨询！');</script>");
                                    }
                                }
                                else
                                {
                                    string loginName1 = eduPersonStudentID[0].ToString();
                                    string loginName2 = loginName1 + "*";
                                    int count = userInfoService.Count(d => (d.uLoginName == loginName1 || d.uLoginName == loginName2) && d.uFrom == 1 && d.uOrgId == orgId && d.uFlagDel == 0);
                                    if (count > 0)
                                    {
                                        if (count == 1)
                                        {
                                            var user = userInfoService.Find(d => (d.uLoginName == loginName1 || d.uLoginName == loginName2) && d.uFrom == 1 && d.uOrgId == orgId && d.uFlagDel == 0);

                                            uLoginName = user.uLoginName;
                                            accessType = type;
                                            string opName = "用户登陆";
                                            string describe = "登陆成功，用户帐号：" + uLoginName;
                                            DTSM.BusinessHallMobile.Extensions.PubMethodExtensions.AddLog(user, opName, describe, accessType, this);
                                            im.Quit();
                                            return Redirect(url);
                                        }
                                        else
                                        {
                                            if (System.Web.HttpContext.Current.Request.Cookies["iPlanetDirectoryPro"] != null)
                                            {
                                                im.DestroySSOToken(System.Web.HttpContext.Current.Request.Cookies["iPlanetDirectoryPro"].Value);
                                                System.Web.HttpContext.Current.Request.Cookies.Remove("iPlanetDirectoryPro");
                                            }
                                            im.Quit();
                                            if (type == "0")
                                            {
                                                return Content("<script>alert('该统一认证帐号对应多个学生网络账号，请到信息服务大厅咨询！');window.location='" + logoutURL + "'</script>");
                                            }
                                            else
                                            {
                                                return Content("<script>alert('该统一认证帐号对应多个学生网络账号，请到信息服务大厅咨询！');</script>");
                                            }
                                        }
                                    }
                                    else
                                    {
                                        if (System.Web.HttpContext.Current.Request.Cookies["iPlanetDirectoryPro"] != null)
                                        {
                                            im.DestroySSOToken(System.Web.HttpContext.Current.Request.Cookies["iPlanetDirectoryPro"].Value);
                                            System.Web.HttpContext.Current.Request.Cookies.Remove("iPlanetDirectoryPro");
                                        }
                                        im.Quit();
                                        if (type == "0")
                                        {
                                            return Content("<script>alert('该统一认证帐号对应的学生网络账号不存在，请到信息服务大厅咨询！');window.location='" + logoutURL + "'</script>");
                                        }
                                        else
                                        {
                                            return Content("<script>alert('该统一认证帐号对应的学生网络账号不存在，请到信息服务大厅咨询！');</script>");
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else
                    {
                        if (System.Web.HttpContext.Current.Request.Cookies["iPlanetDirectoryPro"] != null)
                        {
                            im.DestroySSOToken(System.Web.HttpContext.Current.Request.Cookies["iPlanetDirectoryPro"].Value);
                            System.Web.HttpContext.Current.Request.Cookies.Remove("iPlanetDirectoryPro");
                        }
                        im.Quit();
                        if (type == "0")
                        {
                            return Content("<script>alert('统一认证账号为空，请到信息服务大厅咨询！');window.location='" + logoutURL + "'</script>");
                        }
                        else
                        {
                            return Content("<script>alert('APP认证获取的统一认证账号为空！');</script>");
                        }
                    }
                }
            }
        }

//传url参数：type（1：QQ，2：微信）
//传url参数：code
        public ActionResult HGAccess(string type, string code)
        {
            Organization org = organizationService.Find(o => o.oId == orgId);
            if (org.oName != "华南理工大学")
            {
                return Content("<script>alert('当前组织不支持！');</script>");
            }
            if (string.IsNullOrEmpty(type))
            {
                return Content("<script>alert('入口类型为空！');</script>");
            }
            else
            {
                if (type != "1" && type != "2")
                {
                    return Content("<script>alert('入口类型不在范围内！');</script>");
                }
            }
            if (string.IsNullOrEmpty(code))
            {
                return Content("<script>alert('代码为空！');</script>");
            }

            string url = "";
            if (type == "1")
            {
                url = System.Configuration.ConfigurationManager.AppSettings["QQURL"];
            }
            else
            {
                url = System.Configuration.ConfigurationManager.AppSettings["WXURL"]
            }
            try
            {
                string body = "{\"code\":\"" + code + "\"}";
                string contentType = "application/json";
                var obj = Newtonsoft.Json.Linq.JObject.Parse(DTSM.Common.HttpHelper.PostHttp(url, body, contentType));
                string loginName = "";
                if (obj["result"].ToString() == "success")
                {
                    loginName = obj["account"].ToString();
                    Idstar.CIdentityManagerClass im = new Idstar.CIdentityManagerClass();
                    if (!string.IsNullOrEmpty(loginName))
                    {
                        object[] memberOf = (object[])im.GetUserAttribute_ext(loginName, "memberOf");

                        string memberOfs = "";
                        foreach (var item in memberOf)
                        {
                            memberOfs += item + "#";
                        }
                        //cn=JZG 是教职工；
                        //cn=HTG 是合同工；
                        //cn=BZKS 是本专科生；
                        //cn=YJS 研究生
                        //userType用户类型，0：不允许登录，1：教职工，2：学生
                        int userType = 0;
                        if (memberOfs.Contains("cn=JZG") || memberOfs.Contains("cn=HTG"))
                        {
                            userType = 1;
                        }
                        else if (memberOfs.Contains("cn=BZKS") || memberOfs.Contains("cn=YJS"))
                        {
                            userType = 2;
                        }
                        if (userType == 0)
                        {
                            im.Quit();
                            return Content("<script>alert('该用户所在的统一认证组别不允许登录，请到信息服务大厅咨询！');</script>");
                        }
                        else
                        {
                            if (userType == 1)
                            {
                                string loginName1 = loginName;
                                string loginName2 = loginName1 + "*";
                                int count = userInfoService.Count(d => (d.uLoginName == loginName1 || d.uLoginName == loginName2) && d.uFrom == 2 && d.uOrgId == orgId && d.uFlagDel == 0);
                                if (count > 0)
                                {
                                    if (count == 1)
                                    {
                                        var user = userInfoService.Find(d => (d.uLoginName == loginName1 || d.uLoginName == loginName2) && d.uFrom == 2 && d.uOrgId == orgId && d.uFlagDel == 0);

                                        uLoginName = user.uLoginName;
                                        accessType = type;
                                        string opName = "用户登陆";
                                        string describe = "登陆成功，用户帐号：" + uLoginName;
                                        DTSM.BusinessHallMobile.Extensions.PubMethodExtensions.AddLog(user, opName, describe, accessType, this);
                                        im.Quit();
                                        return Redirect("Index");
                                    }
                                    else
                                    {
                                        im.Quit();
                                        return Content("<script>alert('该统一认证帐号对应多个教工网络账号，请到信息服务大厅咨询！');</script>");
                                    }
                                }
                                else
                                {
                                    im.Quit();
                                    return Content("<script>alert('该统一认证帐号对应的教工网络账号不存在，请到信息服务大厅咨询！');</script>");
                                }
                            }
                            else
                            {
                                object[] eduPersonStudentID = (object[])im.GetUserAttribute_ext(loginName, "eduPersonStudentID");
                                if (string.IsNullOrEmpty(eduPersonStudentID[0].ToString()))
                                {
                                    im.Quit();
                                    return Content("<script>alert('该统一认证帐号对应的学号为空，请到信息服务大厅咨询！');</script>");
                                }
                                else
                                {
                                    string loginName1 = eduPersonStudentID[0].ToString();
                                    string loginName2 = loginName1 + "*";
                                    int count = userInfoService.Count(d => (d.uLoginName == loginName1 || d.uLoginName == loginName2) && d.uFrom == 1 && d.uOrgId == orgId && d.uFlagDel == 0);
                                    if (count > 0)
                                    {
                                        if (count == 1)
                                        {
                                            var user = userInfoService.Find(d => (d.uLoginName == loginName1 || d.uLoginName == loginName2) && d.uFrom == 1 && d.uOrgId == orgId && d.uFlagDel == 0);

                                            uLoginName = user.uLoginName;
                                            accessType = type;
                                            string opName = "用户登陆";
                                            string describe = "登陆成功，用户帐号：" + uLoginName;
                                            DTSM.BusinessHallMobile.Extensions.PubMethodExtensions.AddLog(user, opName, describe, accessType, this);
                                            im.Quit();
                                            return Redirect("Index");
                                        }
                                        else
                                        {
                                            im.Quit();
                                            return Content("<script>alert('该统一认证帐号对应多个学生网络账号，请到信息服务大厅咨询！');</script>");
                                        }
                                    }
                                    else
                                    {
                                        im.Quit();
                                        return Content("<script>alert('该统一认证帐号对应的学生网络账号不存在，请到信息服务大厅咨询！');</script>");
                                    }
                                }
                            }
                        }
                    }
                    else
                    {
                        im.Quit();
                        if (type == "1")
                        {
                            return Content("<script>alert('QQ校园号认证获取的统一认证账号为空！');</script>");
                        }
                        else
                        {
                            return Content("<script>alert('微信企业号认证获取的统一认证账号为空！');</script>");
                        }
                    }
                }
                else
                {
                    if (type == "1")
                    {
                        return Content("<script>alert('QQ校园号认证失败！');</script>");
                    }
                    else
                    {
                        return Content("<script>alert('微信企业号认证失败！');</script>");
                    }
                }
            }
            catch
            {
                if (type == "1")
                {
                    return Content("<script>alert('QQ校园号认证失败！！');</script>");
                }
                else
                {
                    return Content("<script>alert('微信企业号认证失败！！');</script>");
                }
            }
        }

```
