package it1.mock.hailh17.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it1.mock.hailh17.model.Vendor;
import it1.mock.hailh17.services.CountryServices;
import it1.mock.hailh17.services.VendorServices;
import it1.mock.hailh17.validation.Util;
import it1.mock.hailh17.validation.VendorValidation;

/**
 * controller for Vendor
 * @author HaiLH17
 * @BirthDate: 1994/07/07
 */
@Controller
@RequestMapping("/vendor")
public class VendorController {
    @Autowired
    VendorServices vendorSV;

    @Autowired
    CountryServices countrySV;

    @Autowired
    VendorValidation vendorVali;

    /**
     * controller hiển thị thông tin tất cả vendor
     * @author HaiLH17
     * @BirthDate: 1994/07/07
     * @param model
     * @param page
     * @return
     */
    @GetMapping("/list")
    public String showListVenddor(Model model, @RequestParam(defaultValue = "0") Integer page) {
        Pageable pageAble = PageRequest.of(page, Util.PAGE_SIZE);
        Page<Vendor> vendors = vendorSV.getAllVendorWithPage(pageAble);
        if (vendors.isEmpty()) {
            model.addAttribute("mest", "There is no vendor data in the system");
        }
        model.addAttribute("vendors", vendors.getContent());
        model.addAttribute("nameFilter", vendorSV.getAllVendor());
        model.addAttribute("countries", countrySV.getAllCountry());
        model.addAttribute("crPage", page);
        model.addAttribute("ttPage", vendors.getTotalPages());
        return "list";
    }

    /**
     * controller filter thông tin vendor theo vendorName và countryName
     * 
     * @author HaiLH17
     * @BirthDate: 1994/07/07
     * @param vdName
     * @param ctry
     * @param model
     * @param page
     * @return
     */
    @GetMapping("/filter")
    public String filterVendor(@RequestParam(name = "vdName", required = false, defaultValue = "") String vdName,
            @RequestParam(name = "ctry", required = false, defaultValue = "") String ctry, Model model,
            @RequestParam(defaultValue = "0") Integer page) {
        if ("".equals(vdName) && "".equals(ctry)) {
            return "redirect:/vendor/list";
        }
        Pageable pageAble = PageRequest.of(page, Util.PAGE_SIZE);
        Page<Vendor> vendors = vendorSV.getAllVendorByNameOrCountry(pageAble, vdName, ctry);

        if (vendors.isEmpty()) {
            model.addAttribute("mest", "No data found");
        }
        model.addAttribute("nameFilter", vendorSV.getAllVendor());
        model.addAttribute("vendors", vendors.getContent());
        model.addAttribute("vdName", vdName);
        model.addAttribute("ctry", ctry);
        model.addAttribute("countries", countrySV.getAllCountry());
        model.addAttribute("crPage", page);
        model.addAttribute("ttPage", vendors.getTotalPages());
        return "filter";
    }

    /**
     * controller hiển thị thông tin vendor sang trang update
     * 
     * @author HaiLH17
     * @BirthDate: 1994/07/07
     * @param model
     * @param id
     * @param rd
     * @return
     */
    @GetMapping("/update/{id}")
    public String showFormUpdate(Model model, @PathVariable("id") String id, RedirectAttributes rd) {
        Optional<Vendor> vdOp = vendorSV.getVendorByID(id);
        if (!vdOp.isPresent()) {
            rd.addFlashAttribute("udError", "The selected record has been removed by someone");
            return "redirect:/vendor/list";
        }
        Vendor vendor = vdOp.get();
        model.addAttribute("countries", countrySV.getAllCountry());
        model.addAttribute("vendor", vendor);
        return "update";
    }

    /**
     * thực hiện update và trả về màn hình
     * 
     * @author HaiLH17
     * @BirthDate: 1994/07/07
     * @param model
     * @param id
     * @param vd
     * @param rs
     * @param rd
     * @return
     */
    @PostMapping("/update/{id}")
    public String doUpdate(Model model, @PathVariable("id") String id, @Valid @ModelAttribute("vendor") Vendor vd,
            BindingResult rs, RedirectAttributes rd) {
        Optional<Vendor> oldvd = vendorSV.getVendorByID(id);
        if (!oldvd.isPresent()) {
            rd.addFlashAttribute("udError", "The selected record has been removed by someone");
            return "redirect:/vendor/list";
        }
        if (!vd.getVdUpTime().equals(oldvd.get().getVdUpTime())) {
            model.addAttribute("udError",
                    "The selected record has been changed by another user! Please refresh to get the latest data for your update");
            model.addAttribute("countries", countrySV.getAllCountry());
            return "update";
        }
        vendorVali.validate(vd, rs);
        if (rs.hasErrors()) {
            model.addAttribute("countries", countrySV.getAllCountry());
            return "update";
        }
        vendorSV.saveVendor(vd);
        int page = vendorSV.getPageUpdateByIDVendor(id);
        rd.addFlashAttribute("id", id);
        return "redirect:/vendor/list?page=" + page;
    }

    /**
     * controller điều hướng đến màn hình add
     * @author HaiLH17
     * @BirthDate: 1994/07/07
     * @param model
     * @return
     */
    @GetMapping("/add")
    public String showFormAdd(Model model) {
        model.addAttribute("vendor", new Vendor());
        return "add";
    }

}
