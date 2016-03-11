package com.shichai.www.choume.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.globalways.choume.R;
import com.shichai.www.choume.activity.common.WebViewActivity;
import com.shichai.www.choume.tools.CMTool;

/**
 * Created by HeJianjun on 2015/12/22.
 */
public class OptionActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        initActionBar();
        setTitle("设置");
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        String content;
        switch (v.getId()){
            case R.id.tv_normal_question:
                content = "<p class=\"p1\">\n" +
                        "    <span style=\"font-size: 18px;\"></span>\n" +
                        "</p>\n" +
                        "<p class=\"p1\">\n" +
                        "    <span style=\"color: rgb(0, 112, 192);\"><strong>众筹是什么？</strong></span>\n" +
                        "</p>\n" +
                        "<p class=\"p1\">\n" +
                        "    <span class=\"s1\">&nbsp;&nbsp; </span><span style=\"font-size: 14px; color: rgb(89, 89, 89);\">众筹是指项目发起人通过利用互联网和社交网络传播的特性，发动公众的力量，集中公众的资金、能力和渠道为企业或个人进行某项活动提供必要资金援助的一种融资方式，所筹集的资源也不仅仅限于资金，这些资源可以是人脉、渠道、智慧、场地，也可以是经验、技能、商业规则等。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p1\">\n" +
                        "    <br/>\n" +
                        "</p>\n" +
                        "<p class=\"p1\">\n" +
                        "    <span style=\"color: rgb(0, 112, 192);\"><strong>为</strong></span><span style=\"color: rgb(0, 112, 192);\"><strong>什么需要学生证认证？</strong></span>\n" +
                        "</p>\n" +
                        "<p class=\"p1\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">&nbsp;&nbsp;&nbsp;&nbsp;筹么是针对于大学生的众筹平台，为了打造良好的征信体系，保证发起者和支持者的身份背景相似，防止外部闲杂人员对平台上的项目产生负面影响，保证参与人员的纯净，从而有效降低活动进行过程中的安全隐患。因此需要您配合我们完成学生证认证工作。这一认证可以极大的增加其他用户对您的信任，从而为创造有价值的人际关系打造坚实的基础。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p1\">\n" +
                        "    <br/>\n" +
                        "</p>\n" +
                        "<p class=\"p1\">\n" +
                        "    <span style=\"color: rgb(0, 112, 192);\"><strong>不同类别众筹的价值所在？</strong></span>\n" +
                        "</p>\n" +
                        "<p class=\"p1\">\n" +
                        "    &nbsp;&nbsp;&nbsp;&nbsp;筹乐子<span class=\"s1\">——</span><span style=\"font-size: 14px; color: rgb(89, 89, 89);\">在这里你可以策划一场盛大的狂欢，组织一次惬意的旅行，或早营造只属于两个人的甜蜜。总之，任何你不想一个人做或是一个人做不了的事都可以找到一个TA，说不定就是命中注定。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p1\">\n" +
                        "    <span class=\"s2\">&nbsp;&nbsp;&nbsp;&nbsp;筹票子</span>——<span style=\"font-size: 14px; color: rgb(89, 89, 89);\">在这里你可以消除所有的囊中羞涩，Do what you want to do,be what you want to be,money will never be your problem.</span>\n" +
                        "</p>\n" +
                        "<p class=\"p1\">\n" +
                        "    &nbsp;&nbsp;&nbsp;&nbsp;筹爱心<span class=\"s1\">——</span><span style=\"font-size: 14px; color: rgb(89, 89, 89);\">在这里你可以发起一场慈善募捐，组织一次爱心义演，或者为某只可怜的单身狗筹个有爱心的姑娘。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p1\">\n" +
                        "    &nbsp;&nbsp;&nbsp;&nbsp;校园合伙人<span style=\"font-size: 14px; color: rgb(89, 89, 89);\">——如果你有正在运作的商业项目，如果你有一个好的idea，告诉我们吧，我们会竭尽所能为您提供资金募集、市场推广、媒体设计、团队建设等一系列服务，只要你有梦，我们就愿意和你齐休戚，共进退！</span>\n" +
                        "</p>\n" +
                        "<p class=\"p1\">\n" +
                        "    <br/>\n" +
                        "</p>\n" +
                        "<p class=\"p1\">\n" +
                        "    <span style=\"color: rgb(0, 112, 192);\"><strong>众筹一般规则</strong></span><span style=\"color: rgb(0, 112, 192);\"><strong>？</strong></span>\n" +
                        "</p>\n" +
                        "<p class=\"p1\">\n" +
                        "    &nbsp;&nbsp;&nbsp;&nbsp;筹资<span class=\"s1\">——</span><span style=\"font-size: 14px; color: rgb(89, 89, 89);\">筹资项目必须在发起人预设的时间内达到或超过目标金额才算成</span><span style=\"font-size: 14px; color: rgb(89, 89, 89);\">功。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p1\">\n" +
                        "    <span class=\"s1\">&nbsp; &nbsp;&nbsp;</span>未达成<span class=\"s1\">——</span><span style=\"font-size: 14px; color: rgb(89, 89, 89);\">没有达到目标的项目，支持款项将全额退还给所有支持者。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p1\">\n" +
                        "    <span class=\"s1\">&nbsp; &nbsp;&nbsp;</span>达成<span class=\"s1\">——</span><span style=\"font-size: 14px; color: rgb(89, 89, 89);\">网友将得到发起人预先承诺的回报</span>\n" +
                        "</p>\n" +
                        "<p class=\"p1\">\n" +
                        "    <br/>\n" +
                        "</p>\n" +
                        "<p class=\"p1\">\n" +
                        "    <span style=\"color: rgb(0, 112, 192);\"><strong>怎样打造出有吸引力的项目？</strong></span>\n" +
                        "</p>\n" +
                        "<p class=\"p1\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">&nbsp;&nbsp;&nbsp;&nbsp;精美的图片、精彩的文字都能增加项目对支持者的吸引力，如果项目的背后还隐藏着动人的故事，或许更能引发支持者的情感共鸣，使你的项目更容易成功。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p1\">\n" +
                        "    <br/>\n" +
                        "</p>\n" +
                        "<p class=\"p1\">\n" +
                        "    <span style=\"color: rgb(0, 112, 192);\"><strong>怎样设置筹资回报？</strong></span>\n" +
                        "</p>\n" +
                        "<p class=\"p1\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">&nbsp;&nbsp;&nbsp;&nbsp;建议回报为项目的衍生品，</span><span style=\"font-size: 14px; color: rgb(89, 89, 89);\">同时设置三个梯度的回报，多些选择能提高项目的支持率，能让你的项目更快成功。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p1\">\n" +
                        "    <br/>\n" +
                        "</p>\n" +
                        "<p class=\"p1\">\n" +
                        "    <span style=\"color: rgb(0, 112, 192);\"><strong>我们可以提供哪些服务？</strong></span>\n" +
                        "</p>\n" +
                        "<p class=\"p1\">\n" +
                        "    <span class=\"s1\">&nbsp; &nbsp;&nbsp;</span><span style=\"font-size: 14px; color: rgb(89, 89, 89);\">筹么可以为您提供项目营销服务，包含</span><span style=\"font-size: 14px; color: rgb(89, 89, 89);\">项目在APP内的重点推广，包装设计、网站或者APP开发等一系列配套服务。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p1\">\n" +
                        "    <br/>\n" +
                        "</p>\n" +
                        "<p class=\"p1\">\n" +
                        "    <span style=\"color: rgb(0, 112, 192);\"><strong>项目成功后的相关事宜？</strong></span>\n" +
                        "</p>\n" +
                        "<p class=\"p1\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">&nbsp;&nbsp;&nbsp;&nbsp;项目筹资成功后将在截止日期后将款项打至发起人账户，同时发起人着手履行之前承诺给支持者的回报。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p1\">\n" +
                        "    <br/>\n" +
                        "</p>\n" +
                        "<p class=\"p1\">\n" +
                        "    <span style=\"color: rgb(0, 112, 192);\"><strong>项目失败如何处理</strong></span><span style=\"color: rgb(0, 112, 192);\"><strong>？</strong></span>\n" +
                        "</p>\n" +
                        "<p class=\"p1\">\n" +
                        "    <span style=\"color: rgb(89, 89, 89); font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;项目失败后，我们将为支持者办理退款，资金将原路返回到支持者的付款账户中，款项将在7到15个工作日内到账，您无需申请。</span>\n" +
                        "</p>\n" +
                        "<p>\n" +
                        "    <br/>\n" +
                        "</p>";
                intent = new Intent(OptionActivity.this, WebViewActivity.class);
                intent.putExtra(CMTool.WEBVIEW_TITLE, "常见问题");
                intent.putExtra(CMTool.WEBVIEW_CONTENT_HTML, content);
                startActivity(intent);
                break;
            case R.id.tv_agreement:
                content = "<p class=\"p1\" style=\"text-align: center;\">\n" +
                        "    用户注册服务协议\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">筹么是成都时柴信息科技有限公司（简称“筹么”）运营的网络平台，本协议的双方是筹么的注册用户（简称“您”）与筹么。在您注册并使用“筹么”提供的各项服务之前，请您务必仔细阅读并透彻理解本协议条款。如果您同意注册并使用“筹么”提供的服务，视为您已经认真阅读、理解并完全接受了本协议全部内容，本协议即时在您与筹么之间发生法律效力。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">以下为本协议内容：</span>\n" +
                        "</p>\n" +
                        "<p class=\"p3\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">一、 定义</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">1、项目发起人：在筹么平台注册并发起众筹项目的单位或个人；<br/>2、项目投资人：在筹么平台注册并投资某众筹项目的单位或个人。<br/>3、保证金：项目发起人在筹么平台发起的项目，由筹么作为代收款方，收取项目投资人基于项目投资明细单而支付的众筹金额。项目众筹成功后，筹么留存项目众筹总金额的30%作为保证金，在项目发起人兑现对项目投资人的回报承诺后，筹么将该保证金返还给项目发起人。如果项目发起人未兑现对项目投资人的回报承诺，则筹么有权直接支配该保证金以用于兑现对项目投资人的回报承诺。<br/>4、资金支付渠道费：为保证众筹资金安全，众筹项目项下资金均通过第三方支付公司代为划转，由此产生的支付渠道费由项目发起人负担，项目众筹成功后，筹么将扣除项目众筹总金额的2%作为资金支付渠道费，并支付给第三方支付机构。如众筹项目不成功，则不收取该笔费用。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p3\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">二、本协议的构成及效力</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">1、所有筹么已经发布的或将来发布的各类规则，作为本协议不可分割的一部分，与本协议具有同等的法律效力。<br/>2、筹么有权根据需要适时修订本协议及各类规则，并在网站上公示。变更后的协议和规则一经在筹么站上公示，即发生法律效力。<br/>3、在使用筹么提供的服务之前，请务必认真阅读本协议的全部内容。如您对本协议有任何疑问，应向筹么咨询。本协议生效后，您不应以未阅读或不接受本协议的内容为由，主张本协议无效或要求撤销本协议。<br/>4、您应该按照本协议约定行使权利并履行义务。如您不能接受本协议的约定，包括但不限于不能接受修订后的协议及各类规则，则您应立即停止使用筹么针对项目发起人提供的服务。如您继续使用筹么针对项目发起人提供的服务，则表示您同意并接受本协议及各类规则的约束。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p3\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">三、项目发起人的资格</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">1、作为项目发起人，您应为筹么的注册用户，完全理解并接受本协议。<br/>2、作为项目发起人的单位应为依法成立并登记备案的企业法人或其他组织；作为项目发起人的个人，应为年满18周岁并具有完全民事行为能力和民事权利能力的自然人，如项目发起人未满18周岁，应由其监护人代为履行本协议权利和义务。<br/>3、您应按照筹么的要求，进行必要的身份认证和资质认证，包括但不限于身份证、护照、学历证明等的认证。<br/>4、您应拥有在中国大陆地区开户并接收人民币汇款的银行卡或支付宝账户。<br/>5、您应妥善保管在筹么的用户名和密码，凡使用您的用户名和密码登陆筹么进行的一切操作，均视为您本人的行为，一切责任由您本人承担。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p3\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">四、您发起的众筹项目应符合以下要求</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">1、您在筹么上发起的项目，应明确具体的开始时间和结束时间。截止项目结束时间，如项目众筹金额低于预定众筹金额，则项目众筹失败；如项目众筹金额等于或大于预定众筹金额，则项目众筹成功。<br/>2、你不应抄袭、盗用他人的成果发起众筹项目，创意类产品必须为原创。<br/>3、用户保证其使用筹么服务时将遵守国家、地方法律法规、遵从行业惯例和社会公共道德，不会利用筹么提供的服务进行存储、发布、传播如下信息和内容：违反国家法律法规政策的任何内容（信息）；违反国家规定的政治宣传和新闻信息；涉及国家秘密和国家安全的信息；封建迷信和淫秽、色情、下流的信息或教唆犯罪的信息；博彩有奖、赌博游戏；违反国家民族和宗教政策的信息；防碍互联网运行安全的信息；侵害他人合法权益的信息和其他有损于社会秩序、社会治安、公共道德的信息或内容。用户同时承诺不得为他人发布上述不符合国家规定和本服务条款约定的信息内容提供任何便利，包括但不限于设置URL、BANNER链接等。用户承认筹么有权在用户违反上述约定时有权终止向用户提供服务并不予退还任何款项，因用户上述行为给筹么造成损失的，用户应予赔偿。<br/>4、您在筹么发起的项目，不得在国内外同类筹么站同时发起。<br/>5、您在筹么上发起的项目不得涉及种族主义、宗教极端主义、恐怖主义等内容。<br/>6、您应对自己及您发起的项目进行介绍，同时，您应向项目投资人充分说明项目存在的风险及挑战，以便于项目投资人对项目有全面充分的了解，从而独立慎重作出是否投资的决定。<br/>7、您发起的项目应内容完整、合理，具有可行性。<br/>8、您发起的项目不应与第三方存在任何权利纠纷，否则因此导致的一切损失（包括筹么因此被第三方权利追索而遭受的一切损失）由您本人承担，与筹么无关。<br/>9、在项目发起后的运行过程中，您应及时回复网友的提问，与项目投资人进行充分互动，促成项目众筹成功。<br/>10、项目发起后，您应及时更新项目进展情况，以包括但不限于以照片、视频、素描等方式展示项目的进度信息。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p3\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">五、项目回报</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">1、您在发起项目时，应明确项目众筹成功后的回报时间。<br/>2、您承诺，如项目众筹成功，及时兑现对项目投资人承诺的回报；如项目众筹失败，同意筹么将众筹款项及时退还项目投资人，并由您就项目众筹失败的原因等对项目投资人作出解释。<br/>3、如您与项目投资人在兑现回报过程中发生纠纷，一切责任由您本人承担，如因此给筹么造成经济或名誉损失，您应赔偿损失、恢复名誉。<br/>4、如您在兑现对项目投资人的回报过程中，与第三方（包括但不限于物流公司）发生纠纷，一切责任由您本人承担，与筹么无关。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p3\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">六、协议终止</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">1、以下任一情形出现，本协议即终止<br/>&nbsp;（1）您发起的项目违反了国家法律法规、政策或筹么的平台使用规则或本协议约定，筹么停止为您提供服务的；<br/>&nbsp;（2）您本人不同意接受本协议约定（含筹么发布的各类规则），并停止使用筹么针对项目发起人提供的服务的；<br/>&nbsp;（3）您不符合本协议约定的项目发起人应具备的资格的；<br/>&nbsp;（4）其他原因导致本协议终止的。<br/>2、本协议终止后，不影响您因在本协议终止前的行为应承担的义务和责任。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p3\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">七、协议其它款项</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\"><br/>1、您应是具有完全民事权利能力和民事行为能力的自然人，或依法设立并有效存续的单位（包括企业法人及其他组织）。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">2、您应妥善保管您的登录用户名和密码，使用您的用户名和密码登陆筹么进行的任何操作，将视为您本人的操作，一切后果由您本人承担。如果您的用户名或密码泄露，您应立即通知筹么，以避免可能的损失。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">3、您在注册时及在使用筹么提供的服务时提供的相关资料及信息应真实、准确、有效、完整，且不存在误导陈述，不侵害其他人的知识产权及合法权益。前述资料及信息包括：您在注册或登录过程中、在任何公共信息区域（包括留言栏或反馈区）或通过任何电邮形式或手机短信向筹么或其他用户提供的所有内容。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">4、为更好地保护您的合法权益并向您提供优质、高效的服务，筹么可能将您的信息应用在以下方面：</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">&nbsp; &nbsp; （1）在您登陆筹么时进行必要的身份验证；</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">&nbsp; &nbsp; （2）根据您的浏览记录及相关信息，向您推荐您可能感兴趣的项目资料；</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">&nbsp; &nbsp; （3）针对您通过筹么提出的任何问题及时予以回复，并提供必要的协助；</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">&nbsp; &nbsp; （4）分析筹么的用户需求及使用情况，以为您提供更优质的服务；</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">&nbsp; &nbsp; （5）在项目众筹成功后，将项目支持人的姓名/名称及联系方式发送给项目发起人，以协助项目发起人兑现对项目支持人的回报；</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">&nbsp; &nbsp; （6）用于向您提供的其他相关服务，比如向您发送筹么的有关通知、服务公告等。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">5、筹么将针对您的个人交易向您发送相关电子邮件，同时也会将此类邮件的发送控制在最低限度，如果您不选择退订系类邮件，视为您接受筹么继续向您发送此类邮件。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">6、您在对筹么发布的项目提供众筹支持时，应认真阅读筹么发布的有关该项目的一切信息。如果您确定为某项目提供众筹支持，视为您已经完全理解并接受该项目的相关内容及风险，一切后果由您本人承担，筹么不承担任何责任。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">7、筹么不保证筹么发布的所有项目均能够众筹成功，如项目众筹失败，筹么会协助项目发起人将众筹金额无息返还给项目支持人，项目发起人或项目支持人均不得要求筹么承担任何责任。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">8、如筹么上发布的项目众筹成功，则筹么会协助、监督项目发起人兑现对项目支持人的回报，如项目发起人与项目支持人因此产生纠纷，应双方协商解决，筹么可提供必要的协助，但任何一方均不得要求筹么承担任何责任。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">9、筹么应对您提供的信息履行保密义务，非为本协议约定之目的或按照法律、法规、有权机关的要求进行信息披露外，未经您事先书面同意，筹么不应向任何第三方披露您的信息。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">10、您在使用筹么提供的服务时应遵守中华人民共和国相关法律法规、不得将本服务用于任何非法目的，也不得以任何非法方式使用本服务。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">11、当您存在包括但不限于下列情形之一时，筹么有权立即终止向您提供服务而无需承担任何责任，并有权要求您赔偿筹么因此遭受的一切损失：</span>\n" +
                        "</p>\n" +
                        "<p class=\"p4\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">&nbsp; &nbsp; &nbsp; &nbsp;11.1&nbsp;违反本协议的约定；</span>\n" +
                        "</p>\n" +
                        "<p class=\"p4\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">&nbsp; &nbsp; &nbsp; &nbsp;11.2&nbsp;冒用他人名义使用本服务；</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">&nbsp; &nbsp; &nbsp; &nbsp;11.3&nbsp;利用本服务侵害他人的合法权益；</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">&nbsp; &nbsp; &nbsp; &nbsp;11.4&nbsp;从事非法交易，如洗钱、贩卖枪支、毒品、禁药、盗版软件、淫秽物品等；</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">&nbsp; &nbsp; &nbsp; &nbsp;11.5&nbsp;从事任何可能含有电脑病毒或可能侵害本服务系统之行为；</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">&nbsp; &nbsp; &nbsp; &nbsp;11.6&nbsp;筹么认为向您提供本服务存在风险的；</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">&nbsp; &nbsp; &nbsp; &nbsp;11.7&nbsp;其他导致筹么终止向您提供服务的行为。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">12、您理解并同意，筹么不对因下述任一情况导致的您的任何损害承担赔偿责任，包括但不限于利润、商誉、使用、数据等方面的损失或其他无形损失的损害赔偿&nbsp;(无论筹么是否已被告知该等损害赔偿发生的可能性)：</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">&nbsp; &nbsp; &nbsp; &nbsp;12.1 筹么基于单方判断（包括但不限于筹么认为您已经违反相关法律法规或本协议的约定），暂停、中止或终止向您提供全部或部分服务，并移除您的资料。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">&nbsp; &nbsp; &nbsp; &nbsp;12.2 如果筹么发现您违反法律法规或本协议约定使用筹么提供的服务，或认为您存在违反法律规定或本协议约定的可能时，有权不经通知先行暂停或终止向您提供全部或部分服务，且无需向您承担任何责任。 &nbsp;</span>\n" +
                        "</p>\n" +
                        "<p class=\"p5\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">&nbsp;&nbsp; &nbsp; 12.3 筹么所提供的服务包括一定的线下活动，在您参与线下活动项目之前，应充分了解潜在的风险，平台只提供信息发布服务，对用户因线下活动造成的伤害或损失，平台不承担相关责任。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p5\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">&nbsp;&nbsp; &nbsp; 12.4 线下活动中，发起人和参与者发生利益冲突时，双方自行协商解决，筹么可作为中间人进行调停并达成和解</span>\n" +
                        "</p>\n" +
                        "<p class=\"p6\">\n" +
                        "    <br/>\n" +
                        "</p>\n" +
                        "<p class=\"p7\">\n" +
                        "    <br/>\n" +
                        "</p>\n" +
                        "<p class=\"p8\">\n" +
                        "    <br/>\n" +
                        "</p>\n" +
                        "<p class=\"p8\">\n" +
                        "    <br/>\n" +
                        "</p>\n" +
                        "<p class=\"p9\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">13、因以下任一情形导致您无法使用全部或部分服务时，筹么不承担任何责任，该情形包括但不限于：</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">&nbsp; &nbsp; &nbsp; &nbsp;13.1 筹么在网站公告之系统停机维护期间；</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">&nbsp; &nbsp; &nbsp; &nbsp;13.2 电信设备出现故障不能进行数据传输的；</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">&nbsp; &nbsp; &nbsp; &nbsp;13.3 您的银行卡发卡银行的原因导致的服务中断或延迟；</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">&nbsp; &nbsp; &nbsp; &nbsp;13.4 因台风、地震、海啸、洪水、停电、战争、恐怖袭击等不可抗力之因素，造成筹么系统障碍不能执行业务的；</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">&nbsp; &nbsp; &nbsp; &nbsp;13.5 由于黑客攻击、电信部门技术调整或故障、网站升级等原因而造成的服务中断或者延迟。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">14、筹么依据本协议约定中止或终止向您提供服务后，并不能免除您在中止或终止前实施的行为应承担的任何法律后果。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">15、本协议的成立、生效、履行，均适用中华人民共和国法律解释。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">16、筹么发布的各类规则、协议、条款等，构成本协议不可分割的组成部分。筹么有权适时调整本协议的内容，并在筹么公布，如您不能接受变更后的协议内容，请立即停止使用筹么提供的服务。如您继续使用筹么提供的服务，视为您理解并接受调整后的本协议。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">17、双方在履行本协议的过程中，如发生争议，应协商解决。协商不成的，应提交当地法院诉讼解决。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">18、通知</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">&nbsp; &nbsp; &nbsp; &nbsp;18.1&nbsp;除非另行明示载明，任何通知将发往您在注册过程中向筹么提供的电邮地址或直接在网站上公告，筹么也可以以适当的其他方式进行通知。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">&nbsp; &nbsp; &nbsp; &nbsp;18.2&nbsp;任何通知应视为于以下时间送达：</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">&nbsp; &nbsp; &nbsp; &nbsp;(i)&nbsp;如通过电邮发送，则电邮发送后24个小时，但发送方被告知电邮地址无效的，则属例外；</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">&nbsp; &nbsp; &nbsp; &nbsp;(ii)&nbsp;如以预付邮资的信件发送，则投邮之日后三个营业日；</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">&nbsp; &nbsp; &nbsp; &nbsp;(iii)&nbsp;如寄往或寄自中国，则在投邮后第七个营业日；</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">&nbsp; &nbsp; &nbsp; &nbsp;(iv)&nbsp;如通过传真发送，则传真发出的该个营业日（只要发送人收到载明以上传真号码、发送页数和发送日期的确认报告）。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p2\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">19、您同意，在发生并购时，筹么在本协议和所有纳入协议的条款和规则项下的所有或者部分权利和义务，可由筹么自行酌情决定向第三方进行转让。</span>\n" +
                        "</p>\n" +
                        "<p class=\"p3\">\n" +
                        "    <span style=\"font-size: 14px; color: rgb(89, 89, 89);\">八、本协议的解释权归成都时柴信息科技有限公司所有</span>\n" +
                        "</p>\n" +
                        "<p>\n" +
                        "    <br/>\n" +
                        "</p>";
                intent = new Intent(OptionActivity.this, WebViewActivity.class);
                intent.putExtra(CMTool.WEBVIEW_TITLE, "用户协议");
                intent.putExtra(CMTool.WEBVIEW_CONTENT_HTML, content);
                startActivity(intent);
                break;
            case R.id.tv_about_us:
                content = "<p class=\"p1\" style=\"text-align: left;\">\n" +
                        "    筹么是全国最大的校园众筹平台，是由成都时柴信息科技有限公司运营的集社交娱乐、创业扶持、项目对接，人才匹配等一系列服务的综合平台。在这里，你可以拓展人脉，可以规划未来，可以让你的大学更精彩！\n" +
                        "</p>\n" +
                        "<p>\n" +
                        "    <br/>\n" +
                        "</p>";
                intent = new Intent(OptionActivity.this, WebViewActivity.class);
                intent.putExtra(CMTool.WEBVIEW_TITLE, "关于筹么");
                intent.putExtra(CMTool.WEBVIEW_CONTENT_HTML, content);
                startActivity(intent);
                break;
            case R.id.tv_update:
                Toast.makeText(this,"暂无",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
