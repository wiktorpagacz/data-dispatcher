package com.pagacz.mail.service;

import com.pagacz.database.dto.BaseDto;
import com.pagacz.mail.utils.HtmlStyle;
import com.pagacz.mail.utils.HtmlTags;

import java.util.List;
import java.util.Objects;

public class MailCreatorService<T extends BaseDto> {

    private static final String LINK_NAME = "LINK";

    public String createMailFromOffers(List<T> dataList) {
        StringBuilder mailMessage = new StringBuilder();
        if (Objects.nonNull(dataList) && !dataList.isEmpty()) {
            int iterator = 0;
            createTableHeader(mailMessage, dataList.get(0));
            for (T t : dataList) {
                createOfferRow(mailMessage, t, iterator);
                iterator++;
            }
            mailMessage.append(HtmlTags.TABLE_STYLE.getCloseTag());
        }
        return mailMessage.toString();
    }

    private void createTableColumn(StringBuilder builder, String columnName, HtmlStyle style) {
        builder.append(String.format(HtmlTags.TD_STYLE.getOpenTag(), style.getStyle())).append(columnName).append(HtmlTags.TD_STYLE.getCloseTag());
    }

    private void createTableHeader(StringBuilder tableBuilder, T t) {
        tableBuilder.append(String.format(HtmlTags.TABLE_STYLE.getOpenTag(), HtmlStyle.TABLE.getStyle()));
        tableBuilder.append(HtmlTags.THEAD_DEFAULT.getOpenTag());
        tableBuilder.append(HtmlTags.TR_DEFAULT.getOpenTag());
        prepareHeadColumns(tableBuilder, t);
        tableBuilder.append(HtmlTags.TR_DEFAULT.getCloseTag());
        tableBuilder.append(HtmlTags.THEAD_DEFAULT.getCloseTag());
    }

    private void prepareHeadColumns(StringBuilder tableBuilder, T t) {
        List <String> headers = t.getMailTableHeaders();
        headers.add(0, "Nr");
        headers.forEach(colName -> createTableColumn(tableBuilder, colName, HtmlStyle.TH));
    }

    private void createOfferRow(StringBuilder builder, T t, int rowNumber) {
        builder.append(String.format(HtmlTags.TR_STYLE.getOpenTag(), rowNumber % 2 == 0 ? HtmlStyle.TR_EVEN.style : HtmlStyle.TR_ODD.style));
        createTableColumn(builder, String.valueOf(rowNumber), HtmlStyle.TD);
        for (Object o : t.getColumnsForMail()) {
            String value = String.valueOf(o);
            if (isLink(value)) {
                createTableColumn(builder, createAnchor(value), HtmlStyle.TD);
            } else {
                createTableColumn(builder, value, HtmlStyle.TD);
            }
        }
        builder.append(HtmlTags.TR_DEFAULT.getCloseTag());
    }

    private boolean isLink(String value) {
        return value.contains("www") || value.contains(".pl") || value.contains(".com");
    }

    private String createAnchor(String href) {
        return String.format("<a href=\"%s\">%s</a>", href, MailCreatorService.LINK_NAME);
    }
}
