#include <jni.h>
#include <vector>

//-----------------------------1----------------------------------

/*
  Розділ 1. Сторінка 1.


  En – власний показник ефективності n-го завдання системи ХБРЯ захисту;

  R0 – допустимий ризик досягнення мети бойових дій щодо n-го завдання системи
  ХБРЯ захисту;

  Rn  – імовірний ризик досягнення мети бойових дій до планування n-го завдання
  системи ХБРЯ захисту або очікуваний ризик досягнення мети бойових дій після
  планування n-го завдання системи ХБРЯ захисту.
*/

extern "C" JNIEXPORT jfloat JNICALL Java_ua_nure_cbrnprotector_ui_En_EnViewModel_En(
        JNIEnv *env, jobject /* this */, jfloat Rn, jfloat R0) {
    return 1 - (Rn - R0) / Rn;
}

/*
  Розділ 1.1. Сторінка 4.


  Rny – показник ймовірного ризику недостатнього рівня ураження об’єктів
  противника РПВ;

  Py – показник імовірності влучення ТББ у об’єкти противника;

  Vy – показник ступеня ураження об’єктів противника РПВ.
*/

extern "C" JNIEXPORT jfloat JNICALL Java_ua_nure_cbrnprotector_ui_Rnu_RnuViewModel_Rny(
        JNIEnv *env, jobject /* this */, jfloat Py, jfloat Vy) {
    return 1 - Py * Vy;
}

/*
  Розділ 1.2. Сторінка 5.


  R0 – показник допустимого ризику досягнення мети бойових дій щодо втрати життя
і здоров’я особового складу в умовах РХ зараження;

  P0 – показник імовірності РХ зараження особового складу підрозділів ;

  V0 – показник максимально допустимої уразливості особового складу підрозділів
в умовах РХ зараження.
*/

extern "C" JNIEXPORT jfloat JNICALL Java_ua_nure_cbrnprotector_ui_R0_R0ViewModel_R0(
        JNIEnv *env, jobject /* this */, jfloat P0, jfloat V0) {
    return P0 * V0;
}

/*
  Розділ 1.2. Сторінка 6.


  V0 – показник максимально допустимої уразливості особового складу підрозділів
  в умовах РХ зараження;

  N0_ivt – кількість особового складу i-ї військової частини , який ймовірно
  втратить життя та здоров’я в умовах РХ зараження;

  N_oc – загальна кількість особового складу .
*/

extern "C" JNIEXPORT jfloat JNICALL
Java_ua_nure_cbrnprotector_ui_V0_V0ViewModel_V0(
        JNIEnv *env,
        jobject /* this */,
        jint sum_N_ivt,
        jint N_oc) {
    return static_cast<float>(sum_N_ivt) / N_oc;
}

/*
  Розділ 2. Сторінка 7.

  Rrhz – показник ймовірного ризику досягнення мети бойових дій щодо втрати
  життя і здоров’я особового складу підрозділів в умовах РХ зараження;

  Prhz – показник імовірності РХ зараження особового складу підрозділів ;

  Vrhz – показник уразливості особового складу підрозділів в умовах РХ
  зараження.
*/

extern "C" JNIEXPORT jfloat JNICALL
Java_ua_nure_cbrnprotector_ui_Rrhz_RrhzViewModel_Rrhz(JNIEnv *env, jobject /* this */, jfloat Prhz,
                                             jfloat Vrhz) {
    return Prhz * Vrhz;
}


/*
  Розділ 2.1. Сторінка 7.

  Увага!!!!

  Функція Pjirhz використовується в функції Prhz.

  Prhz – показник імовірності РХ зараження особового складу підрозділів ;

  Pjirhz – показник імовірності реалізації j-х загроз РХ зараження i-ї
  військової частини за наявною інформацією старшого начальника, даними
  розвідки;

  N_rhz – кількість і-х військових частин де реалізуються загрози
  РХ зараження.

  D_j_irhz – кількість j-х загроз РХ зараження і-ї військової частини за даними
  розвідки;

  n_irhz – загальна кількість визначених загроз, реалізація яких 100 % призведе
  до втрат особового складу і-ї військової частини в умовах РХ зараження

*/

extern "C"
JNIEXPORT jfloat JNICALL
Java_ua_nure_cbrnprotector_MainActivity_Pjirhz(JNIEnv *env, jobject /* this */, jint D_j_irz,
                                               jint n_irhz) {
    return static_cast<jfloat>(D_j_irz) / n_irhz;
}

extern "C"
JNIEXPORT jfloat JNICALL
Java_ua_nure_cbrnprotector_MainActivity_Prhz(JNIEnv *env, jobject /* this */, jintArray D_j_irz,
                                             jintArray n_irhz, jint N_rhz) {
    jfloat sum = 0;
    jboolean isCopy;
    jint *D_j_irz_ptr = env->GetIntArrayElements(D_j_irz, &isCopy);
    jint *n_irhz_ptr = env->GetIntArrayElements(n_irhz, &isCopy);
    for (int i = 0; i < N_rhz; i++) {
        sum += static_cast<jfloat>(D_j_irz_ptr[i]) / n_irhz_ptr[i];
    }
    env->ReleaseIntArrayElements(D_j_irz, D_j_irz_ptr, JNI_ABORT);
    env->ReleaseIntArrayElements(n_irhz, n_irhz_ptr, JNI_ABORT);
    return sum / N_rhz;
}

/*
  Розділ 2.1. Сторінка 7.

  Увага!!!!

  Функція N200, N300 використовується в функції Vrhz.

  N200 – можливі втрати життя особового складу і-ї військової частини в умовах
  РХ зараження;

  N300 – можливі втрати здоров’я особового складу і-ї військової частини в
  умовах РХ зараження;

  N_oc – загальна кількість особового складу .

  N_rhz – кількість і-х військових частин де реалізуються загрози
  РХ зараження.

  Vrhz – показника уразливості особового складу підрозділів в умовах РХ
  зараження

*/

extern "C" JNIEXPORT jint JNICALL
Java_ua_nure_cbrnprotector_MainActivity_N200(JNIEnv *env, jobject thiz, jintArray N_200_ios,
                                             jint N_rhz) {
    jint *N_200_ios_ptr = env->GetIntArrayElements(N_200_ios, 0);
    jint sum200 = 0;
    for (int i = 0; i < N_rhz; i++) {
        sum200 += N_200_ios_ptr[i];
    }
    env->ReleaseIntArrayElements(N_200_ios, N_200_ios_ptr, 0);
    return sum200;
}

extern "C" JNIEXPORT jint JNICALL
Java_ua_nure_cbrnprotector_MainActivity_N300(JNIEnv *env, jobject thiz, jintArray N_300_ios,
                                             jint N_rhz) {
    jint *N_300_ios_ptr = env->GetIntArrayElements(N_300_ios, 0);
    jint sum300 = 0;
    for (int i = 0; i < N_rhz; i++) {
        sum300 += N_300_ios_ptr[i];
    }
    env->ReleaseIntArrayElements(N_300_ios, N_300_ios_ptr, 0);
    return sum300;
}

extern "C" JNIEXPORT jfloat JNICALL
Java_ua_nure_cbrnprotector_MainActivity_Vrhz(JNIEnv *env, jobject thiz, jint N200, jint N300,
                                             jint N_oc) {
    return static_cast<float>(N200 + N300) / N_oc;
}

/*
  Сторінка 8. Пункт 2.3

  Rrhz_Critical – показник критичного ризику досягнення мети бойових дій щодо
  втрати життя і здоров’я особового складу в умовах РХ зараження;

  R0 – показник допустимого ризику досягнення мети бойових дій щодо втрати життя
  і здоров’я особового складу в умовах РХ зараження;

  Rrhz – показник ймовірного ризику досягнення мети бойових дій щодо втрати
  життя і здоров’я особового складу підрозділів в умовах РХ зараження;

*/

extern "C" JNIEXPORT jfloat JNICALL
Java_ua_nure_cbrnprotector_ui_RrhzCrit_RrhzCritViewModel_RrhzCritical(JNIEnv *env, jobject thiz, jfloat Rrhz,
                                                      jfloat R0) {
    return Rrhz - R0;
}

/*
  Сторінка 8. Пункт 2.3

  Ehbrya_necessary – обчислення необхідної ефективності виконання заходів ХБРЯ
  захисту:

  R0 – показник допустимого ризику досягнення мети бойових дій щодо втрати життя
  і здоров’я особового складу в умовах РХ зараження;

*/

extern "C" JNIEXPORT jfloat JNICALL
Java_ua_nure_cbrnprotector_ui_EhbryaNec_EhbryaNecViewModel_EhbryaNecessary(JNIEnv *env, jobject thiz, float R0) {
    return (1 - R0);
}

/*
  Сторінка 8. Пункт 2.3

  Ehbrya_0 - обчислення ефективності виконання заходів ХБРЯ захисту військовими
  частинами без підрозділів РХБ захисту

  Rrhz_Critical – показник критичного ризику досягнення мети бойових дій щодо
  втрати життя і здоров’я особового складу в умовах РХ зараження;

  Rrhz – показник ймовірного ризику досягнення мети бойових дій щодо втрати
  життя і здоров’я особового складу підрозділів в умовах РХ зараження;

*/

extern "C" JNIEXPORT jfloat JNICALL
Java_ua_nure_cbrnprotector_ui_Ehbrya0_Ehbrya0ViewModel_Ehbrya0(JNIEnv *env, jobject thiz, jfloat Rrhz_Critical,
                                                 jfloat Rrhz) {
    return 1 - (Rrhz_Critical / Rrhz);
}

/*

  Сторінка 8. Пункт 2.5. Формула 15.

  R_hbrya_rhz_och – показник очікуваного ризику досягнення мети бойових дій щодо
  втрати здоров’я особового складу підрозділів в умовах РХ зараження після
  спланованих заходів ХБРЯ захисту;

  P_hbrya_rhz_och – показник очікуваної імовірності РХ зараження особового
  складу підрозділів після спланованих заходів ХБРЯ захисту;

  V_hbrya_rhz_och– показник очікуваної уразливості особового складу підрозділів
  в умовах РХ зараження після спланованих заходів ХБРЯ захисту.

*/

extern "C" JNIEXPORT jfloat JNICALL
Java_ua_nure_cbrnprotector_MainActivity_R_hbrya_rhz_och(JNIEnv *env, jobject thiz,
                                                        jfloat P_hbrya_rhz_och,
                                                        jfloat V_hbrya_rhz_och) {
    return P_hbrya_rhz_och * V_hbrya_rhz_och;
}

/*

  Сторінка 9. Пункт 2.6. Формула 18.

*/

extern "C" JNIEXPORT jint JNICALL
Java_ua_nure_cbrnprotector_ui_Wvo_WvoViewModel_WVO(JNIEnv *env, jobject thiz,
                                             jint w_rhr,
                                             jint w_rhk,
                                             jint w0) {
    return w_rhr + w_rhk + w0;
}

/*

  Сторінка 9. Пункт 2.6. Формула 19.

*/

extern "C" JNIEXPORT jint JNICALL
Java_ua_nure_cbrnprotector_MainActivity_W_PJ(JNIEnv *env, jobject thiz,
                                             jint w_on,
                                             jint w_zikz,
                                             jint w_poz) {
    return w_on + w_poz + w_zikz;
}

/*

  Сторінка 9. Пункт 2.6. Формула 20.

*/

extern "C" JNIEXPORT jint JNICALL
Java_ua_nure_cbrnprotector_MainActivity_W_LN(JNIEnv *env, jobject thiz,
                                             jint w_oc,
                                             jint w_ovt,
                                             jint w_m) {
    return w_oc + w_ovt + w_m;
}

/*

  Сторінка 9. Пункт 2.6. Формула 17.

*/


extern "C" JNIEXPORT jint JNICALL
Java_ua_nure_cbrnprotector_ui_Whbrya_WhbryaViewModel_WHBRYA(JNIEnv *env, jobject thiz,
                                                jint W_VO,
                                                jint W_PG,
                                                jint W_LN) {
    return W_VO + W_PG + W_LN;
}

/*

  Сторінка 10. Пункт 2.7. Формула 21.

*/

extern "C" JNIEXPORT jfloat JNICALL
Java_ua_nure_cbrnprotector_MainActivity_N_j_i(JNIEnv *env, jobject thiz, jfloatArray w_i,
                                              jfloatArray a_i, jint j) {
    jfloat *w_i_ptr = env->GetFloatArrayElements(w_i, 0);
    jfloat *a_i_ptr = env->GetFloatArrayElements(a_i, 0);
    jfloat sum = 0.0;
    for (int i = 0; i <= j; i++) {
        sum += w_i_ptr[i];
    }
    jfloat result = sum / a_i_ptr[j];
    env->ReleaseFloatArrayElements(w_i, w_i_ptr, 0);
    env->ReleaseFloatArrayElements(a_i, a_i_ptr, 0);
    return result;
}

/*

  Сторінка 10. Пункт 2.7. Формула 22.

*/

extern "C" JNIEXPORT jfloat JNICALL
Java_ua_nure_cbrnprotector_MainActivity_K_n(JNIEnv *env, jobject thiz, jfloatArray w_i,
                                            jfloatArray a_i, jfloatArray N_i_um) {
    jfloat *w_i_ptr = env->GetFloatArrayElements(w_i, 0);
    jfloat *a_i_ptr = env->GetFloatArrayElements(a_i, 0);
    jfloat *N_i_um_ptr = env->GetFloatArrayElements(N_i_um, 0);
    jfloat K = 0.0;
    int size = env->GetArrayLength(w_i);
    for (int i = 0; i < size; i++) {
        jfloat N_i_j = Java_ua_nure_cbrnprotector_MainActivity_N_j_i(env, thiz, w_i, a_i, i);
        K += N_i_j / N_i_um_ptr[i];
    }
    env->ReleaseFloatArrayElements(w_i, w_i_ptr, 0);
    env->ReleaseFloatArrayElements(a_i, a_i_ptr, 0);
    env->ReleaseFloatArrayElements(N_i_um, N_i_um_ptr, 0);
    return K;
}

/*

  Сторінка 10. Пункт 2.8. Формула 23.

*/

extern "C" JNIEXPORT jfloat JNICALL
Java_ua_nure_cbrnprotector_MainActivity_C_n(JNIEnv *env, jobject thiz, jfloatArray N_i,
                                            jfloatArray c_i) {
    jfloat *N_i_ptr = env->GetFloatArrayElements(N_i, 0);
    jfloat *c_i_ptr = env->GetFloatArrayElements(c_i, 0);
    jfloat C = 0.0;
    int size = env->GetArrayLength(N_i);
    for (int i = 0; i < size; i++) {
        C += N_i_ptr[i] * c_i_ptr[i];
    }
    env->ReleaseFloatArrayElements(N_i, N_i_ptr, 0);
    env->ReleaseFloatArrayElements(c_i, c_i_ptr, 0);
    return C;
}

/*

  Сторінка 11. Пункт 2.9. Формула 24.

*/


extern "C" JNIEXPORT jfloat JNICALL
Java_ua_nure_cbrnprotector_MainActivity_Ehbrya(JNIEnv *env, jobject thiz,
                                               jfloat Rrhz_och_Critical,
                                               jfloat Rrhz_och) {
    return 100 - (Rrhz_och_Critical / Rrhz_och) * 100;
}

/*

  Сторінка 11. Пункт 2.9. Формула 24.

*/

extern "C" JNIEXPORT jfloat JNICALL
Java_ua_nure_cbrnprotector_MainActivity_V_hbrya_rhz_on(JNIEnv *env, jobject thiz,
                                                       jfloat N_yr,
                                                       jfloat N_oc) {
    return N_yr / N_oc;
}