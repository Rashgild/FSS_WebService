package HelpersMethods;

/**
 * Created by rkurbanov on 10.11.16.
 */
public class SQLStoreQuer {

    public static String SelectLNN()
    {
        String s = GlobalVariables.DisabilityDocument_id;
        String selectLNN="select number from disabilitydocument where id ="+GlobalVariables.DisabilityDocument_id;

        return selectLNN;
    }

    public static String SQL_Req()
    {
        String s = GlobalVariables.DisabilityDocument_id;
        String SqlReq="select dd.id as DDID  \n" +
                "                        ,dd.patient_id as DD_PAT  \n" +
                "                        ,dc.patient_id as DC_PAT  \n" +
                "                         ,p.snils as snils \n" +
                "                         ,p.lastname as surname  \n" +
                "                        ,p.firstname as name  \n" +
                "                        ,p.middlename as patronimic\n" +
                "                        ,case when (dc.placementservice is not null or dc.placementservice ='1') then '1' else '0' end as BOZ_FLAG \n" +
                "                        ,dd.job as LPU_EMPLOYER  \n" +
                "                        ,dd.workcombotype_id as LPU_EMPL_FLAG \n" +
                "                        ,dd.number as ln_code  \n" +
                "                        ,vddp.code as PRIMARY_FLAG\n" +
                "                        ,case when (select count(a.id) from disabilitydocument a where a.duplicate_id=dd.id) >0 then '1' else '0'end as duplicate_flag --111\n" +
                "                        ,dd.issuedate as ln_date\n" +
                "                        ,case when ml2.id is not null then ml2.name else ml1.name end as lpu_name\n" +
                "                        ,case when ml2.id is not null then ml2.printaddress else ml1.printaddress end as lpu_address \n" +
                "                        ,case when ml2.id is not null then ml2.ogrn else ml1.ogrn end as lpu_ogrn\n" +
                "                        ,p.birthday as birthday    \n" +
                "                        ,case when sex.omccode = '1' then '0' else '1' end as gender  \n" +
                "                        ,vdr.codef as REASON1  \n" +
                "                        ,vdr2.code as REASON2 \n" +
                "                        ,vdr3.code as REASON3 \n" +
                "                        ,mkb.code as diagnos\n" +
                "                        ,dd.mainworkdocumentnumber as PARENT_CODE \n" +
                "                        ,dd.sanatoriumdatefrom as date1 \n" +
                "                        ,dd.sanatoriumdateto as date2\n" +
                "                        ,dd.sanatoriumticketnumber as voucher_no \n" +
                "                        ,dd.sanatoriumogrn as voucher_ogrn \n" +
                "                        ,case when p1.id is not null and p1.id!=p.id then to_char(p1.birthday,'yyyy-MM-dd') else to_char(p12.birthday,'yyyy-MM-dd') end as serv1_age\n" +
                "                        ,case when p1.id is not null and p1.id!=p.id then vkr1.code else vkr1.oppositeRoleCode end as serv1_relation_code\n" +
                "                        ,case when p1.id is not null and p1.id!=p.id then p1.lastname||' '||p1.firstname||' '||p1.middlename else p12.lastname||' '||p12.firstname||' '||p12.middlename end as serv1_fio\n" +
                "                        ,case when p2.id is not null and p2.id!=p.id then to_char(p2.birthday,'yyyy-MM-dd') else to_char(p22.birthday,'yyyy-MM-dd') end as serv2_age \n" +
                "                        ,case when p2.id is not null and p2.id!=p.id then vkr2.code else vkr2.oppositeRoleCode end as serv2_relation_code\n" +
                "                        ,case when p2.id is not null and p2.id!=p.id then p2.lastname||' '||p2.firstname||' '||p2.middlename else p22.lastname||' '||p22.firstname||' '||p22.middlename end as serv2_fio \n" +
                "                        --,p2.lastname||' '||p2.firstname||' '||p2.middlename as serv2_fio\n" +
                "                        ,case when (dc.earlypregnancyregistration is not null or dc.earlypregnancyregistration ='1') then '1' else '' end as PREGN12W_FLAG  \n" +
                "                        ,dd.hospitalizedfrom as HOSPITAL_DT1  \n" +
                "                        ,dd.hospitalizedto as HOSPITAL_DT2  \n" +
                "                        ,dd.closereason_id as closereason\n" +
                "                        ,dd.otherclosedate as other_state_dt \n" +
                "                        ,dd3.number as NEXT_LN_CODE \n" +
                "                        ,dd.status_id as ln_state\n" +
                "            ,mss.assignmentdate as mse_dt1 \n" +
                "            ,mss.registrationdate as mse_dt2\n" +
                "            ,mss.examinationdate as mse_dt3\n" +
                "            ,vi.code  \n" +
                "                        ,disrec.datefrom as tread_dt1 \n" +
                "                        ,disrec.dateto as tread_dt2\n" +
                "                        ,vwf.name as tread_doctor_role\n" +
                "                        ,docname.lastname ||' '|| docname.firstname ||' '|| docname.middlename as treat_doctor \n" +
                "                        ,vwf2.name as TREAT_CHAIRMAN_ROLE --должность вк\n" +
                "                        ,vkname.lastname ||' '|| vkname.firstname ||' '|| vkname.middlename as TREAT_CHAIRMAN --фио вк\n" +
                "                        ,rvr.datefrom as HOSPITAL_BREACH_CODE           \n" +
                "                        ,rvr.regimeviolationtype_id as HOSPITAL_BREACH_DT\n" +
                "                        ,dd.isclose     \n" +
                "                        --,docname.id as DocId\n" +
                "                        ,dd.idc10_id as none_giagnosis  \n" +
                "                        ,dd.disabilitycase_id as none_discase  \n" +
                "                        ,dd.documenttype_id as none_ln_type \n" +
                "                        ,dd.hospitalizednumber   \n" +
                "                        -- ,dd.duplicate_id as f_duplicate  \n" +
                "                        --,case when (select dd.id from disabilitydocument b, disabilitydocument c where b.id=c.duplicate_id and b.id=dd.id) is not null then '1' else '0'end as dublicate --111\n" +
                "                        --,p2.birthday as serv2_age \n" +
                "                        --,vkr2.code as serv2_relation \n" +
                "                        ,dd2.number as prevDocument \n" +
                "                        ,coalesce(vddcr.codef,'') as mse_result  \n" +
                "                        from disabilitydocument dd    \n" +
                "                        left join (select min(datefrom) as datefrom,disabilitydocument_id from disabilityrecord  group by disabilitydocument_id) as dr on dr.disabilitydocument_id=dd.id    \n" +
                "                        left join vocdisabilitydocumentclosereason vddcr on vddcr.id = dd.closereason_id \n" +
                "                        left join disabilitydocument dd3 on dd3.prevdocument_id=dd.id \n" +
                "                        left join disabilitydocument dd2 on dd2.id=dd.prevdocument_id \n" +
                "                        left join disabilitycase dc on dc.id=dd.disabilitycase_id \n" +
                "                        left join patient p on p.id=dc.patient_id \n" +
                "                        left join vocsex sex on sex.id=p.sex_id \n" +
                "                        left join vocidc10 mkb on mkb.id=dd.idc10final_id \n" +
                "                        left join vocdisabilityreason vdr on vdr.id=dd.disabilityreason_id \n" +
                "                        left join vocdisabilityreason vdr3 on vdr3.id=dd.disabilityreasonchange_id \n" +
                "                        left join kinsman k1 on k1.id=dc.nursingperson1_id \n" +
                "                        left join vockinsmanrole vkr1 on vkr1.id=k1.kinsmanrole_id \n" +
                "                        left join patient p1 on p1.id=k1.kinsman_id \n" +
                "                        left join patient p12 on p12.id=k1.person_id \n" +
                "                        left join kinsman k2 on k2.id=dc.nursingperson2_id \n" +
                "                        left join vockinsmanrole vkr2 on vkr2.id=k2.kinsmanrole_id \n" +
                "                        left join patient p2 on p2.id=k2.kinsman_id \n" +
                "                        left join patient p22 on p22.id=k2.person_id \n" +
                "                        left join statisticstub ss on ss.code=dd.hospitalizednumber and ss.year=cast(to_char(dd.hospitalizedfrom,'yyyy')as int) \n" +
                "                        left join medcase mc on mc.id=ss.medcase_id \n" +
                "                        left join mislpu ml1 on ml1.id=mc.lpu_id \n" +
                "                        left join mislpu ml2 on ml2.id=ml1.parent_id \n" +
                "                        left join vocdisabilityreason2 vdr2 on vdr2.id=dd.disabilityreason2_id \n" +
                "                        left join vocdisabilitydocumentprimarity vddp on vddp.id=dd.primarity_id\n" +
                "                        left join disabilityrecord disrec on disrec.disabilitydocument_id = dd.id\n" +
                "                        left join workfunction wf on wf.id = disrec.workfunction_id \n" +
                "                        left join worker w on w.id = wf.worker_id\n" +
                "                        left join patient docname on docname.id = w.person_id \n" +
                "                        left join VocWorkFunction vwf on vwf.id = wf.workFunction_id\n" +
                "                        left join workfunction wf2 on wf2.id = disrec.workfunctionadd_id\n" +
                "                        left join worker w2 on w2.id = wf2.worker_id\n" +
                "                        left join patient vkname on vkname.id = w2.person_id\n" +
                "                        left join VocWorkFunction vwf2 on vwf2.id = wf2.workFunction_id\n" +
                "                        left join regimeviolationrecord rvr on rvr.disabilitydocument_id = dd.id\n" +
                "            left join medsoccommission mss on mss.disabilitydocument_id=dd.id\n" +
                "            left join vocinvalidity vi on vi.id=mss.invalidity_id\n" +
                "                        where dd.exportdate is null  \n" +
                "                        and dd.anotherlpu_id is null  \n" +
                "                        --and dd.isclose='1' \n" +
                "                        and (dd.noactuality is null or dd.noactuality='0') \n" +
                "                        and (ss.dtype='StatisticStubExist' or ss.id is null) \n" +
                "                     --   and dc.patient_id=343577\n" +
                "            and dd.id ="+s+
                "                        order by dd.issuedate desc \n" +
                "                        --limit 10";

        return SqlReq;
    }
}
