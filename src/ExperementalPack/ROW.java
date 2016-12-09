package ExperementalPack;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

/**
 * Created by rkurbanov on 05.12.2016.
 */
@XmlRootElement(name = "ROWSET")
@XmlType(propOrder = {
        "snils",
        "surname",
        "name",
        "patronimic",
        "bozflag",
        "lpuemployer",
        "lpuemplflag",
        "lncode",
        "prevlncode",
        "primaryflag",
        "duplicateflag",
        "lndate",
        "lpuname",
        "lpuaddress",
        "lpuogrn",
        "birthday",
        "gender",
        "reason1",
        "reason2",
        "reason3",
        "diagnos",
        "parentcode",
        "date1",
        "date2",
        "voucherno",
        "voucherogrn",
        "serv1AGE",
        "serv1MM",
        "serv1RELATIONCODE",
        "serv1FIO",
        "serv1DT1",
        "serv1DT2",
        "serv2AGE",
        "serv2MM",
        "serv2RELATIONCODE",
        "serv2FIO",
        "serv2DT1",
        "serv2DT2",
        "pregn12WFLAG",
        "hospitaldt1",
        "hospitaldt2",
        "hospitalbreach",
        "msedt1",
        "msedt2",
        "msedt3",
        "mseinvalidgroup",
        "TREAT_PERIODS",
        "lnresult",
        "lnstate",
        "lnhash"
})
public class ROW {


    protected String AttribId;
    protected String snils;
    protected String surname;
    protected String name;
    protected String patronimic;
    protected int bozflag;
    protected String lpuemployer;
    protected int lpuemplflag;
    protected String lncode;
    protected String prevlncode;
    protected int primaryflag;
    protected int duplicateflag;
    protected String lndate;
    protected String lpuname;
    protected String lpuaddress;
    protected String lpuogrn;
    protected String birthday;
    protected int gender;
    protected String reason1;
    protected String reason2;
    protected String reason3;
    protected String diagnos;
    protected String parentcode;
    protected String date1;
    protected String date2;
    protected String voucherno;
    protected String voucherogrn;
    protected String serv1AGE;
    protected Integer serv1MM;
    protected String serv1RELATIONCODE;
    protected String serv1FIO;
    protected String serv1DT1;
    protected String serv1DT2;
    protected String serv2AGE;
    protected Integer serv2MM;
    protected String serv2RELATIONCODE;
    protected String serv2FIO;
    protected String serv2DT1;
    protected String serv2DT2;
    protected String pregn12WFLAG;
    protected String hospitaldt1;
    protected String hospitaldt2;
    // @XmlElement(name = "HOSPITAL_BREACH")
    protected List<HOSPITAL_BREACH>hospitalbreach;
    protected String msedt1;
    protected String msedt2;
    protected String msedt3;
    protected Integer mseinvalidgroup;
    //@XmlElement(name = "TREAT_PERIODS", required = true)
    protected List<LN_RESULT> lnresult;
    protected String lnstate;
    protected String lnhash;
    protected List<TREAT_FULL_PERIOD> TREAT_PERIODS;

    public List<TREAT_FULL_PERIOD> getTREAT_PERIODS() {
        return TREAT_PERIODS;
    }


    public String getAttribId() {
        return AttribId;
    }


    @XmlAttribute(name = "Id", namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd")
    public void setAttribId(String attribId) {
        AttribId = attribId;
    }

    @XmlElement(name = "TREAT_FULL_PERIOD", required = true)
    @XmlElementWrapper
    public void setTREAT_PERIODS(List<TREAT_FULL_PERIOD> TREAT_PERIODS) {
        this.TREAT_PERIODS = TREAT_PERIODS;
    }

    public List<LN_RESULT> getLnresult() {
        return lnresult;
    }

    @XmlElement(name = "LN_RESULT")
    public void setLnresult(List<LN_RESULT> lnresult) {
        this.lnresult = lnresult;
    }

    public List<HOSPITAL_BREACH> getHospitalbreach() {
        return hospitalbreach;
    }

    @XmlElement(name = "HOSPITAL_BREACH")
    public void setHospitalbreach(List<HOSPITAL_BREACH> hospitalbreach) {
       this.hospitalbreach = hospitalbreach;
    }

    public String getSnils() {
        return snils;
    }
    @XmlElement(name = "SNILS", required = true)
    public void setSnils(String snils) {
        this.snils = snils;
    }
    public String getSurname() {
        return surname;
    }
    @XmlElement(name = "SURNAME", required = true)
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getName() {
        return name;
    }
    @XmlElement(name = "NAME", required = true)
    public void setName(String name) {
        this.name = name;
    }
    public String getPatronimic() {
        return patronimic;
    }
    @XmlElement(name = "PATRONIMIC")
    public void setPatronimic(String patronimic) {
        this.patronimic = patronimic;
    }
    public int getBozflag() {
        return bozflag;
    }
    @XmlElement(name = "BOZ_FLAG")
    public void setBozflag(int bozflag) {
        this.bozflag = bozflag;
    }
    public String getLpuemployer() {
        return lpuemployer;
    }
    @XmlElement(name = "LPU_EMPLOYER")
    public void setLpuemployer(String lpuemployer) {
        this.lpuemployer = lpuemployer;
    }
    public int getLpuemplflag() {
        return lpuemplflag;
    }
    @XmlElement(name = "LPU_EMPL_FLAG")
    public void setLpuemplflag(int lpuemplflag) {
        this.lpuemplflag = lpuemplflag;
    }

    public String getLncode() {
        return lncode;
    }
    @XmlElement(name = "LN_CODE", required = true)
    public void setLncode(String lncode) {
        this.lncode = lncode;
    }
    public String getPrevlncode() {
        return prevlncode;
    }
    @XmlElement(name = "PREV_LN_CODE")
    public void setPrevlncode(String prevlncode) {
        this.prevlncode = prevlncode;
    }
    public int getPrimaryflag() {
        return primaryflag;
    }
    @XmlElement(name = "PRIMARY_FLAG")
    public void setPrimaryflag(int primaryflag) {
        this.primaryflag = primaryflag;
    }
    public int getDuplicateflag() {
        return duplicateflag;
    }
    @XmlElement(name = "DUPLICATE_FLAG")
    public void setDuplicateflag(int duplicateflag) {
        this.duplicateflag = duplicateflag;
    }

    public String getLndate() {
        return lndate;
    }
    @XmlElement(name = "LN_DATE", required = true)
    public void setLndate(String lndate) {
        this.lndate = lndate;
    }

    public String getLpuname() {
        return lpuname;
    }
    @XmlElement(name = "LPU_NAME", required = true)
    public void setLpuname(String lpuname) {
        this.lpuname = lpuname;
    }

    public String getLpuaddress() {
        return lpuaddress;
    }
    @XmlElement(name = "LPU_ADDRESS")
    public void setLpuaddress(String lpuaddress) {
        this.lpuaddress = lpuaddress;
    }

    public String getLpuogrn() {
        return lpuogrn;
    }
    @XmlElement(name = "LPU_OGRN", required = true)
    public void setLpuogrn(String lpuogrn) {
        this.lpuogrn = lpuogrn;
    }
    public String getBirthday() {
        return birthday;
    }
    @XmlElement(name = "BIRTHDAY", required = true)
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public int getGender() {
        return gender;
    }
    @XmlElement(name = "GENDER")
    public void setGender(int gender) {
        this.gender = gender;
    }
    public String getReason1() {
        return reason1;
    }
    @XmlElement(name = "REASON1", required = true)
    public void setReason1(String reason1) {
        this.reason1 = reason1;
    }
    public String getReason2() {
        return reason2;
    }
    @XmlElement(name = "REASON2", required = true)
    public void setReason2(String reason2) {
        this.reason2 = reason2;
    }
    public String getReason3() {
        return reason3;
    }
    @XmlElement(name = "REASON3", required = true)
    public void setReason3(String reason3) {
        this.reason3 = reason3;
    }
    public String getDiagnos() {
        return diagnos;
    }
    @XmlElement(name = "DIAGNOS")
    public void setDiagnos(String diagnos) {
        this.diagnos = diagnos;
    }
    public String getParentcode() {
        return parentcode;
    }
    @XmlElement(name = "PARENT_CODE")
    public void setParentcode(String parentcode) {
        this.parentcode = parentcode;
    }
    public String getDate1() {
        return date1;
    }
    @XmlElement(name = "DATE1", required = true)
    public void setDate1(String date1) {
        this.date1 = date1;
    }
    public String getDate2() {
        return date2;
    }
    @XmlElement(name = "DATE2", required = true)
    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public String getVoucherno() {
        return voucherno;
    }
    @XmlElement(name = "VOUCHER_NO")
    public void setVoucherno(String voucherno) {
        this.voucherno = voucherno;
    }

    public String getVoucherogrn() {
        return voucherogrn;
    }

    @XmlElement(name = "VOUCHER_OGRN")
    public void setVoucherogrn(String voucherogrn) {
        this.voucherogrn = voucherogrn;
    }

    public String getServ1AGE() {
        return serv1AGE;
    }

    @XmlElement(name = "SERV1_AGE", required = true, type = String.class)
    public void setServ1AGE(String serv1AGE) {
        this.serv1AGE = serv1AGE;
    }


    public Integer getServ1MM() {
        return serv1MM;
    }

    @XmlElement(name = "SERV1_MM", required = true, type = Integer.class)
    public void setServ1MM(Integer serv1MM) {
        this.serv1MM = serv1MM;
    }

    public String getServ1RELATIONCODE() {
        return serv1RELATIONCODE;
    }
    @XmlElement(name = "SERV1_RELATION_CODE")
    public void setServ1RELATIONCODE(String serv1RELATIONCODE) {
        this.serv1RELATIONCODE = serv1RELATIONCODE;
    }

    public String getServ1FIO() {
        return serv1FIO;
    }

    @XmlElement(name = "SERV1_FIO")
    public void setServ1FIO(String serv1FIO) {
        this.serv1FIO = serv1FIO;
    }

    public String getServ1DT1() {
        return serv1DT1;
    }
    @XmlElement(name = "SERV1_DT1")
    public void setServ1DT1(String serv1DT1) {
        this.serv1DT1 = serv1DT1;
    }

    public String getServ1DT2() {
        return serv1DT2;
    }
    @XmlElement(name = "SERV1_DT2")
    public void setServ1DT2(String serv1DT2) {
        this.serv1DT2 = serv1DT2;
    }

    public String getServ2AGE() {
        return serv2AGE;
    }
    @XmlElement(name = "SERV2_AGE", required = true, type = String.class)
    public void setServ2AGE(String serv2AGE) {
        this.serv2AGE = serv2AGE;
    }

    public Integer getServ2MM() {
        return serv2MM;
    }
    @XmlElement(name = "SERV2_MM", required = true, type = Integer.class)
    public void setServ2MM(Integer serv2MM) {
        this.serv2MM = serv2MM;
    }
    public String getServ2RELATIONCODE() {
        return serv2RELATIONCODE;
    }
    @XmlElement(name = "SERV2_RELATION_CODE")
    public void setServ2RELATIONCODE(String serv2RELATIONCODE) {
        this.serv2RELATIONCODE = serv2RELATIONCODE;
    }
    public String getServ2FIO() {
        return serv2FIO;
    }
    @XmlElement(name = "SERV2_FIO")
    public void setServ2FIO(String serv2FIO) {
        this.serv2FIO = serv2FIO;
    }
    public String getServ2DT1() {
        return serv2DT1;
    }
    @XmlElement(name = "SERV2_DT1", required = true)
    public void setServ2DT1(String serv2DT1) {
        this.serv2DT1 = serv2DT1;
    }
    public String getServ2DT2() {
        return serv2DT2;
    }
    @XmlElement(name = "SERV2_DT2", required = true)
    public void setServ2DT2(String serv2DT2) {
        this.serv2DT2 = serv2DT2;
    }
    public String getPregn12WFLAG() {
        return pregn12WFLAG;
    }
    @XmlElement(name = "PREGN12W_FLAG", required = true, type = String.class)
    public void setPregn12WFLAG(String pregn12WFLAG) {
        this.pregn12WFLAG = pregn12WFLAG;
    }
    public String getHospitaldt1() {
        return hospitaldt1;
    }
    @XmlElement(name = "HOSPITAL_DT1", required = true)
    public void setHospitaldt1(String hospitaldt1) {
        this.hospitaldt1 = hospitaldt1;
    }
    public String getHospitaldt2() {
        return hospitaldt2;
    }
    @XmlElement(name = "HOSPITAL_DT2", required = true)
    public void setHospitaldt2(String hospitaldt2) {
        this.hospitaldt2 = hospitaldt2;
    }
    public String getMsedt1() {
        return msedt1;
    }
    @XmlElement(name = "MSE_DT1", required = true)
    public void setMsedt1(String msedt1) {
        this.msedt1 = msedt1;
    }
    public String getMsedt2() {
        return msedt2;
    }
    @XmlElement(name = "MSE_DT2", required = true)
    public void setMsedt2(String msedt2) {
        this.msedt2 = msedt2;
    }
    public String getMsedt3() {
        return msedt3;
    }
    @XmlElement(name = "MSE_DT3", required = true)
    public void setMsedt3(String msedt3) {
        this.msedt3 = msedt3;
    }
    public Integer getMseinvalidgroup() {
        return mseinvalidgroup;
    }
    @XmlElement(name = "MSE_INVALID_GROUP", required = true, type = Integer.class)
    public void setMseinvalidgroup(Integer mseinvalidgroup) {
        this.mseinvalidgroup = mseinvalidgroup;
    }
    public String getLnstate() {
        return lnstate;
    }
    @XmlElement(name = "LN_STATE", required = true)
    public void setLnstate(String lnstate) {
        this.lnstate = lnstate;
    }
    public String getLnhash() {
        return lnhash;
    }
    @XmlElement(name = "LN_HASH")
    public void setLnhash(String lnhash) {
        this.lnhash = lnhash;
    }
}
