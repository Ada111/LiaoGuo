package com.lbk.app.weiliao.utils;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lbk.app.weiliao.R;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;

public class SmileyParser {
	private static SmileyParser instance;

	public static SmileyParser getInstance() {
		return instance;
	}

	public static void init(Context context) {
		instance = new SmileyParser(context);
	}

	private static Context context;
	private static String[] smileyTexts;
	private static Pattern pattern;
	private static HashMap<String, Integer> smileyMap;

	private SmileyParser(Context context) {
		this.context = context;
		this.smileyTexts = context.getResources().getStringArray(
				R.array.default_smiley_name);
		this.smileyMap = buildSmileyToRes();
		this.pattern = buildPattern();
	}

	public static final int[] DEFAULT_SMILEY_RES_IDS = {
			Smileys.getSmileyResource(Smileys.HEART),
			Smileys.getSmileyResource(Smileys.ROSE),
			Smileys.getSmileyResource(Smileys.SURPRISED),
			Smileys.getSmileyResource(Smileys.PIG),
			Smileys.getSmileyResource(Smileys.CLAP),
			Smileys.getSmileyResource(Smileys.YE),
			Smileys.getSmileyResource(Smileys.PITIFUL),
			Smileys.getSmileyResource(Smileys.LUCKY),
			Smileys.getSmileyResource(Smileys.WINK),
			Smileys.getSmileyResource(Smileys.ANGRY),
			Smileys.getSmileyResource(Smileys.DIZZY),
			Smileys.getSmileyResource(Smileys.PARENT),
			Smileys.getSmileyResource(Smileys.SERIOUS),
			Smileys.getSmileyResource(Smileys.DOZE),
			Smileys.getSmileyResource(Smileys.SWEAT),
			Smileys.getSmileyResource(Smileys.HAPPY),
			Smileys.getSmileyResource(Smileys.CRY),
			Smileys.getSmileyResource(Smileys.DOUBT),
			Smileys.getSmileyResource(Smileys.HEART1),
			Smileys.getSmileyResource(Smileys.ROSE1),
			Smileys.getSmileyResource(Smileys.SURPRISED1),
			Smileys.getSmileyResource(Smileys.PIG1),
			Smileys.getSmileyResource(Smileys.CLAP1),
			Smileys.getSmileyResource(Smileys.YE1),
			Smileys.getSmileyResource(Smileys.PITIFUL1),
			Smileys.getSmileyResource(Smileys.LUCKY1),
			Smileys.getSmileyResource(Smileys.WINK1),
			Smileys.getSmileyResource(Smileys.ANGRY1),
			Smileys.getSmileyResource(Smileys.DIZZY1),
			Smileys.getSmileyResource(Smileys.PARENT1),
			Smileys.getSmileyResource(Smileys.SERIOUS1),
			Smileys.getSmileyResource(Smileys.DOZE1),
			Smileys.getSmileyResource(Smileys.SWEAT1),
			Smileys.getSmileyResource(Smileys.HAPPY1),
			Smileys.getSmileyResource(Smileys.CRY1),
			Smileys.getSmileyResource(Smileys.DOUBT1),
			Smileys.getSmileyResource(Smileys.HEART2),
			Smileys.getSmileyResource(Smileys.ROSE2),
			Smileys.getSmileyResource(Smileys.SURPRISED2),
			Smileys.getSmileyResource(Smileys.PIG2),
			Smileys.getSmileyResource(Smileys.CLAP2),
			Smileys.getSmileyResource(Smileys.YE2),
			Smileys.getSmileyResource(Smileys.PITIFUL2),
			Smileys.getSmileyResource(Smileys.LUCKY2),
			Smileys.getSmileyResource(Smileys.WINK2),
			Smileys.getSmileyResource(Smileys.ANGRY2),
			Smileys.getSmileyResource(Smileys.DIZZY2),
			Smileys.getSmileyResource(Smileys.PARENT2),
			Smileys.getSmileyResource(Smileys.SERIOUS2),
			Smileys.getSmileyResource(Smileys.DOZE2),
			Smileys.getSmileyResource(Smileys.SWEAT2),
			Smileys.getSmileyResource(Smileys.HAPPY2),
			Smileys.getSmileyResource(Smileys.CRY2),
			Smileys.getSmileyResource(Smileys.DOUBT2),
			Smileys.getSmileyResource(Smileys.HEART3),
			Smileys.getSmileyResource(Smileys.ROSE3),
			Smileys.getSmileyResource(Smileys.SURPRISED3),
			Smileys.getSmileyResource(Smileys.PIG3),
			Smileys.getSmileyResource(Smileys.CLAP3),
			Smileys.getSmileyResource(Smileys.YE3),
			Smileys.getSmileyResource(Smileys.PITIFUL3),
			Smileys.getSmileyResource(Smileys.LUCKY3),
			Smileys.getSmileyResource(Smileys.WINK3),
			Smileys.getSmileyResource(Smileys.ANGRY3),
			Smileys.getSmileyResource(Smileys.DIZZY3),
			Smileys.getSmileyResource(Smileys.PARENT3),
			Smileys.getSmileyResource(Smileys.SERIOUS3),
			Smileys.getSmileyResource(Smileys.DOZE3),
			Smileys.getSmileyResource(Smileys.SWEAT3),
			Smileys.getSmileyResource(Smileys.HAPPY3),
			Smileys.getSmileyResource(Smileys.CRY3),
			Smileys.getSmileyResource(Smileys.DOUBT3),
			Smileys.getSmileyResource(Smileys.CRY4),
			Smileys.getSmileyResource(Smileys.DOUBT4),

	};

	private HashMap<String, Integer> buildSmileyToRes() {
		if (DEFAULT_SMILEY_RES_IDS.length != smileyTexts.length) {
			throw new IllegalStateException("Smiley resource ID/text mismatch");
		}

		HashMap<String, Integer> smileyToRes = new HashMap<String, Integer>(
				smileyTexts.length);
		for (int i = 0; i < smileyTexts.length; i++) {
			smileyToRes.put(smileyTexts[i], DEFAULT_SMILEY_RES_IDS[i]);
		}

		return smileyToRes;
	}

	private Pattern buildPattern() {
		StringBuilder patternString = new StringBuilder(smileyTexts.length * 3);
		patternString.append('(');
		for (String s : smileyTexts) {
			patternString.append(Pattern.quote(s));
			patternString.append('|');
		}
		patternString.replace(patternString.length() - 1,
				patternString.length(), ")");
		return Pattern.compile(patternString.toString());
	}

	public CharSequence addSmileySpans(CharSequence text) {
		SpannableStringBuilder builder = new SpannableStringBuilder(text);

		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			int resId = smileyMap.get(matcher.group());
			builder.setSpan(new ImageSpan(context, resId), matcher.start(),
					matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		return builder;
	}
	
	
	public CharSequence addSmileySpansx(CharSequence text) {
		SpannableStringBuilder builder = new SpannableStringBuilder(text);
		
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			int resId = smileyMap.get(matcher.group());
			builder.setSpan(new ImageSpan(context, resId), matcher.start(),
					matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		return builder;
	}
}
