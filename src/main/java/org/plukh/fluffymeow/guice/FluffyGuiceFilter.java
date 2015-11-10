package org.plukh.fluffymeow.guice;

import com.google.inject.servlet.GuiceFilter;

import javax.servlet.annotation.WebFilter;

@WebFilter(filterName = "guiceFilter", urlPatterns = "/*")
public class FluffyGuiceFilter extends GuiceFilter {
}
