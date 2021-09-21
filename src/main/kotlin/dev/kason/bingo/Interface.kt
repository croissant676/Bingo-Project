@file:Suppress("DuplicatedCode")

package dev.kason.bingo

import javafx.beans.property.SimpleStringProperty
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.control.*
import javafx.scene.control.skin.TextFieldSkin
import javafx.scene.paint.Color
import tornadofx.*
import java.awt.Desktop
import java.io.File
import java.net.URI
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period


object BingoMenu : View("Bingo > Menu") {
    override val root = borderpane {
        center {
            vbox {
                label("Bingo!") {
                    addClass(Styles.titleLabel)
                    padding = insets(15)
                }
                alignment = Pos.CENTER
                button("Create Bingo Game") {
                    addClass(Styles.button)
                    addHoverEffect()
                    action {
                        currentState = BingoState.CREATION_MENU
                        replaceWith(CreationMenuView, ViewTransition.Fade(0.5.seconds))
                    }
                    useMaxSize = true
                }
                button("Appearances") {
                    addHoverEffect()
                    action {
                        replaceWith(MinorSettings, ViewTransition.Fade(0.5.seconds))
                    }
                    useMaxSize = true
                }
                button("How to use") {
                    addClass(Styles.button)
                    addHoverEffect()
                    useMaxSize = true
                    action {
                        replaceWith(HowToUseView, ViewTransition.Fade(0.5.seconds))
                    }
                }
                label("Created by Kason Gu") {
                    paddingTop = 30.0
                    addClass(Styles.regularLabel)
                }
                spacing = 10.0
                maxWidth = 250.0
                alignment = Pos.CENTER
                addClass(Styles.defaultBackground)
            }
            addClass(Styles.defaultBackground)
        }
        bottom {
            hbox {
                hbox {
                    alignment = Pos.BOTTOM_LEFT
                    button("Hidden page???") {
                        style {
                            textFill = Styles.lightTextColor // Make it invisible
                            backgroundColor += Styles.themeBackgroundColor
                            padding = box(1.px, 12.px)
                            borderWidth += box(1.px, 3.px)
                            borderRadius += box(0.px)
                            backgroundRadius += box(0.px)
                            borderColor += box(Color.TRANSPARENT)
                            fontFamily = "dubai"
                            fontSize = Styles.sizeOfText
                        }
                        addHoverEffectLight() // Just for this case
                        action {
                            replaceWith(SettingsView, ViewTransition.Fade(0.5.seconds))
                        }
                    }
                    addClass(Styles.defaultBackground)
                }
            }
            addClass(Styles.defaultBackground)
        }
        top {
            hbox {
                button("Secret Easter egg :)") {
                    style {
                        textFill = Styles.lightTextColor
                        backgroundColor += Styles.themeBackgroundColor
                        padding = box(1.px, 12.px)
                        borderWidth += box(1.px, 3.px)
                        borderRadius += box(0.px)
                        backgroundRadius += box(0.px)
                        borderColor += box(Color.TRANSPARENT)
                        fontFamily = "dubai"
                        fontSize = Styles.sizeOfText
                    }
                    addHoverEffectLight()
                    action {
                        Desktop.getDesktop().browse(URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
                    }
                }
                addClass(Styles.defaultBackground)
            }
        }
        addClass(Styles.defaultBackground)
        minHeight = 767.0
        minWidth = 1000.0
    }
}

@Suppress("All")
object SettingsView : View("Bingo > Settings") {
    override val root = borderpane {

        center {
            hbox {
                tabpane {
                    tab("General") {
                        isClosable = false
                        addClass(Styles.tab)
                    }
                    tab("Formatting") {
                        isClosable = false
                        addClass(Styles.tab)
                    }
                    tab("Performance") {
                        content = vbox {
                            hbox {
                                val heap = label("${Runtime.getRuntime().totalMemory() / 1_000_000} MB") {
                                    addClass(Styles.regularLabel)
                                }
                                val free = label("${Runtime.getRuntime().freeMemory() / 1_000_000} MB") {
                                    addClass(Styles.regularLabel)
                                }
                                val max = label("${Runtime.getRuntime().maxMemory() / 1_000_000} MB") {
                                    addClass(Styles.regularLabel)
                                }
                                button("Refresh") {
                                    addHoverEffect()
                                    action {
                                        val mbFree = Runtime.getRuntime().freeMemory() / 1_000_000
                                        heap.text = "${Runtime.getRuntime().totalMemory() / 1_000_000} MB"
                                        free.text = "${if (mbFree < 10) "0$mbFree" else mbFree} MB"
                                        max.text = "${Runtime.getRuntime().maxMemory() / 1_000_000} MB"
                                    }
                                    style {
                                        padding = box(0.px, 0.px, 0.px, 100.px)
                                    }
                                }
                                spacing = 150.0
                            }
                            hbox {
                                label("Total Memory (in MB)") {
                                    addClass(Styles.regularLabel)
                                }
                                label("Free Memory (in MB)") {
                                    addClass(Styles.regularLabel)
                                }
                                label("Max Memory (in MB)") {
                                    addClass(Styles.regularLabel)
                                }
                                spacing = 35.0
                            }
                            spacing = 5.0

                        }
                        isClosable = false
                        addClass(Styles.tab)
                    }
                    tab("Appearance") {
                        isClosable = false
                        addClass(Styles.tab)
                        val appearances = Appearance.values()
                        content = hbox {
                            for (appearance in appearances) {
                                this += appearance.display()
                            }
                            spacing = 10.0
                            style {
                                padding = box(10.px)
                            }
                            addClass(Styles.defaultBackground)
                        }
                    }
                    addClass(Styles.tabPane)
                    useMaxWidth = true
                }
            }
        }
        right {
            button("Exit") {
                addHoverEffect(c("ff4e6c"), c("eb0028"))
                addClass(Styles.redButton)
                action {
                    currentState = BingoState.MENU
                    replaceWith(BingoMenu, ViewTransition.Fade(0.5.seconds))
                    BingoMenu.onDock()
                }
                style(append = true) {
                    paddingTop = 1
                }
            }
            addClass(Styles.defaultBackground)
        }

    }
}

object MinorSettings : View("Bingo > Settings") {
    override val root: Parent = vbox {
        val appearances = Appearance.values()
        hbox {
            for (appearance in appearances) {
                this += appearance.display()
            }
            spacing = 10.0
            style {
                padding = box(10.px)
            }
            addClass(Styles.defaultBackground) // Nah mate
        }
        // Store the button
        hbox {
            button("Exit") {
                addHoverEffect(c("ff4e6c"), c("eb0028"))
                addClass(Styles.redButton)
                action {
                    currentState = BingoState.MENU
                    replaceWith(BingoMenu, ViewTransition.Fade(0.5.seconds))
                    BingoMenu.onDock()
                }
                style(append = true) {
                    paddingTop = 1
                }
            }
            alignment = Pos.CENTER
            addClass(Styles.defaultBackground)
        }
        addClass(Styles.defaultBackground)
        useMaxSize = true
    }
}

object CreationMenuView : View("Bingo > Create Bingo Game") {

    lateinit var toggleGroup: ToggleGroup
    private lateinit var daysSpinner: Spinner<Int>

    override val root = borderpane {
        center {
            vbox {
                hbox {
                    label("Input the number of days to play the game:") {
                        addClass(Styles.regularLabel)
                    }
                    daysSpinner = spinner(1, 100, 5, 1, false) {
                        onScroll = EventHandler {
                            if(it.deltaY < 0) {
                                decrement()
                            } else {
                                increment()
                            }
                        }
                        isEditable = true
                        editor.textProperty().addListener { _, oldValue, newValue ->
                            if (newValue.length > 5) {
                                editor.text = oldValue
                            } else if (!newValue.matches("\\d*".toRegex())) {
                                editor.text = newValue.replace("[^\\d]".toRegex(), "")
                            }
                        }
                    }
                    paddingBottom = 30.0
                    alignment = Pos.CENTER
                    spacing = 30.0
                }
                label("Extra draws should be:") {
                    addClass(Styles.regularLabel)
                }
                toggleGroup = togglegroup()
                vbox {
                    radiobutton("be spread out.", toggleGroup) {
                        isSelected = true
                    }
                    radiobutton("pushed towards the front.", toggleGroup) { }
                    radiobutton("pushed towards the back.", toggleGroup) { }
                    alignment = Pos.CENTER_LEFT
                    paddingLeft = 400.0
                }
                paddingBottom = 30.0
                alignment = Pos.CENTER
                spacing = 30.0
            }
        }
        bottom {
            hbox {
                button("< Back") {
                    addClass(Styles.button)
                    addHoverEffect()
                    action {
                        currentState = BingoState.MENU
                        replaceWith(BingoMenu, ViewTransition.Fade(0.5.seconds))
                    }
                }
                button("Select dates") {
                    addHoverEffect()
                    action {
                        replaceWith(CreationMenuView2, ViewTransition.Fade(0.5.seconds))
                    }
                }
                button("Next > ") {
                    addClass(Styles.button)
                    addHoverEffect()
                    action {
                        days = daysSpinner.value
                        replaceWith(CreationMenu2, ViewTransition.Fade(0.5.seconds))
                    }
                }
                paddingBottom = 30.0
                alignment = Pos.CENTER
                spacing = 30.0
            }
        }
        addClass(Styles.defaultBackground)
    }
}

object CreationMenuView2 : View("Bingo > Create Bingo Game") {

    private lateinit var datepicker: DatePicker

    override val root = borderpane {
        center {
            val time = LocalDateTime.now()
            vbox {
                vbox {
                    label("Select the end date for this bingo game.") {
                        addClass(Styles.regularLabel)
                    }
                    datepicker = datepicker {
                        addClass(Styles.defaultPicker)
                        editor.skin = TextFieldSkin(editor).apply {
                            scaleX = 0.7
                            scaleY = 0.7
                        }
                        val newTime = time.plusDays(10)
                        editor.text = "${newTime.monthValue}/${newTime.dayOfMonth + 1}/${newTime.year}"
                    }
                    spacing = 30.0
                    addClass(Styles.defaultBackground)
                    alignment = Pos.CENTER
                }
                spacing = 30.0
                addClass(Styles.defaultBackground)
                alignment = Pos.CENTER
            }
        }
        bottom {
            hbox {
                button("< Back") {
                    addClass(Styles.button)
                    addHoverEffect()
                    action {
                        assert(currentState == BingoState.CREATION_MENU) {
                            "State not linked"
                        }
                        currentState = BingoState.MENU
                        replaceWith(CreationMenuView, ViewTransition.Fade(0.5.seconds))
                    }
                }
                button("Next > ") {
                    addClass(Styles.button)
                    addHoverEffect()
                    action {
                        val string = datepicker.editor.text
                        val localDate = LocalDate.of(
                            string.substringAfterLast("/").toInt(),
                            string.substringBefore("/").toInt(),
                            string.substring(string.indexOf("/") + 1, string.lastIndexOf("/")).toInt()
                        )
                        val now = LocalDate.now()
                        if (now.isAfter(localDate)) {
                            alert(Alert.AlertType.ERROR, "Selected a date before the current time!")
                        } else {
                            val period = Period.between(localDate, now)
                            days = period.days
                            replaceWith(CreationMenu2, ViewTransition.Fade(0.5.seconds))
                        }
                    }
                }
                alignment = Pos.CENTER
                spacing = 30.0
                paddingBottom = 30.0
            }
        }
        addClass(Styles.defaultBackground)
    }
}

object CreationMenu2 : View("Bingo > Create Bingo Game") {

    private lateinit var spinner: Spinner<Int>
    private lateinit var numCards: Spinner<Int>
    private lateinit var winners: Spinner<Int>

    override val root = borderpane {
        center {
            vbox {
                vbox {
                    label("Seed:") {
                        addClass(Styles.regularLabel)
                    }
                    spinner = spinner(-100000, 100000, 0, enableScroll = true) {
                        isEditable = true
                        editor.textProperty().addListener { _, oldValue, newValue ->
                            if (newValue.length > 7) {
                                editor.text = oldValue
                            } else if (!newValue.matches("[+-]?[0-9][0-9]*".toRegex())) {
                                editor.text = oldValue
                            }
                        }
                    }
                    alignment = Pos.CENTER
                    addClass(Styles.defaultBackground)
                }
                vbox {
                    label("Number of cards:") {
                        addClass(Styles.regularLabel)
                    }
                    numCards = spinner(1, 10000, 1, enableScroll = true) {
                        isEditable = true
                        editor.textProperty().addListener { _, oldValue, newValue ->
                            if (newValue.length > 7) {
                                editor.text = oldValue
                            } else if (!newValue.matches("[+-]?[0-9][0-9]*".toRegex())) {
                                editor.text = oldValue
                            }
                        }
                    }
                    spacing = 30.0
                    alignment = Pos.CENTER
                    addClass(Styles.defaultBackground)
                }
                vbox {
                    label("Number of winners:") {
                        addClass(Styles.regularLabel)
                    }
                    winners = spinner(1, 10000, 1, enableScroll = true) {
                        isEditable = true
                        editor.textProperty().addListener { _, oldValue, newValue ->
                            if (newValue.length > 7) {
                                editor.text = oldValue
                            } else if (!newValue.matches("[+-]?[0-9][0-9]*".toRegex())) {
                                editor.text = oldValue
                            }
                        }
                    }
                    alignment = Pos.CENTER
                    addClass(Styles.defaultBackground)
                }
                alignment = Pos.CENTER
                addClass(Styles.defaultBackground)
            }
        }
        bottom {
            hbox {
                button("< Back") {
                    addHoverEffect()
                    action {
                        assert(currentState == BingoState.CREATION_MENU) {
                            "State not linked"
                        }
                        replaceWith(CreationMenuView, ViewTransition.Fade(0.5.seconds))
                    }
                }
                button("Next > ") {
                    addHoverEffect()
                    action {
                        if (winners.value > numCards.value) {
                            alert(Alert.AlertType.WARNING, "Number of winners cannot be more than the number of cards!", title = "Winners > Cards!")
                        } else {
                            val game = createGame(spinner.value.toLong(), numCards.value)
                            numberOfWinners = winners.value
                            game.desiredNumberOfWinners = numberOfWinners
                            createSimulation()
                            replaceWith(EditingCardView, ViewTransition.Fade(0.5.seconds))
                        }
                    }
                }
                paddingBottom = 30.0
                alignment = Pos.CENTER
                addClass(Styles.defaultBackground)
                spacing = 20.0
            }
        }
    }
}

object FileView : View("Bingo > Find File") {

    var startingLocation: String
        get() = startingItem.value
        set(value) {
            currentFile = File(value).absoluteFile
            startingItem.value = value
        }

    var showFileTextField: Boolean = true
        set(value) {
            field = value
            refresh()
        }
    lateinit var called: FindFileView
    private var currentFile = File("")
    private val startingItem = TreeItem(currentFile.path)
    private val stringProperty = SimpleStringProperty("")
    val string: String get() = stringProperty.get()

    @Suppress("SpellCheckingInspection")
    private lateinit var tview: TreeView<String>
    lateinit var fileName: TextField

    override var root = borderpane {
        center {
            vbox {
                label("Input the location of the file.") {
                    addClass(Styles.titleLabel)
                }
                if (showFileTextField) {
                    fileName = textfield(currentFFView.string) {
                        isEditable = true
                        stringProperty.value = "Hello"
                    }
                } else {
                    fileName = TextField("Irrelevant")
                }
                tview = treeview(startingItem) {
                    addClass(Styles.defaultTreeView)
                }
                addClass(Styles.defaultBackground)
                alignment = Pos.CENTER
            }
        }
        left {
            region {
                style {
                    backgroundColor += Styles.themeBackgroundColor
                }
                minWidth = 70.0
            }
        }
        right {
            region {
                style {
                    backgroundColor += Styles.themeBackgroundColor
                }
                minWidth = 70.0
            }
        }
        tview.selectFirst()
        bottom {
            hbox {
                button("< Back") {
                    addHoverEffect()
                    action {
                        replaceWith(called, ViewTransition.Fade(0.5.seconds))
                    }
                }
                vbox {
//                    val label = label("You must select a file before continuing") {
//                        addClass(Styles.regularLabel)
//                        style {
//                            textFill = c("ff4e6c")
//                        }
//                        isVisible = false
//                    }
                    button("Next >") {
                        addHoverEffect()
                        action {
                            val value = tview.selectedValue
                            called.apply {
                                if (implementExtensionCheck) {
                                    if (fileName.text == null || !fileName.text.endsWith(string.substringAfterLast('.'))) {
                                        if (fileName.text == null) {
                                            label.text = "Must enter a file name!"
                                        } else if (!fileName.text.endsWith(string.substringAfterLast('.'))) {
                                            label.text = "File extension must be \"${string.substringAfterLast('.')}\"!"
                                        } else {
                                            label.text = "Must enter a file path!"
                                        }
                                        fileName.text = label.text
                                    } else {
                                        result = value!! + "\\" + fileName.text
                                        whenFinished(this)
                                    }
                                } else {
                                    if (fileName.text == null) {
                                        fileName.text = "Must enter a file path!"
                                    } else {
                                        result = value!! + "\\" + fileName.text
                                        whenFinished(this)
                                    }
                                }
                            }
                        }
                        spacing = 10.0
                        addClass(Styles.defaultBackground)
                        alignment = Pos.CENTER
                        paddingBottom = 30.0
                    }
                }
                spacing = 10.0
                addClass(Styles.defaultBackground)
                alignment = Pos.CENTER
                paddingBottom = 30.0
            }
        }
    }

    private fun refresh() {
        root = borderpane {
            center {
                vbox {
                    label("Input the location of the file.") {
                        addClass(Styles.titleLabel)
                    }
                    if (showFileTextField) {
                        textfield(currentFFView.string) {
                            isEditable = false
                            stringProperty.value = "Hello"
                        }
                    }
                    tview = treeview(startingItem) {
                        addClass(Styles.defaultTreeView)
                    }
                    addClass(Styles.defaultBackground)
                    alignment = Pos.CENTER
                }
            }
            left {
                region {
                    style {
                        backgroundColor += Styles.themeBackgroundColor
                    }
                    minWidth = 70.0
                }
            }
            right {
                region {
                    style {
                        backgroundColor += Styles.themeBackgroundColor
                    }
                    minWidth = 70.0
                }
            }
            tview.selectFirst()
            bottom {
                hbox {
                    button("< Back") {
                        addHoverEffect()
                        action {
                            replaceWith(called, ViewTransition.Fade(0.5.seconds))
                        }
                    }
                    vbox {
                        button("Next >") {
                            addHoverEffect()
                            action {
                                val value = tview.selectedValue
                                called.apply {
                                    if (implementExtensionCheck) {
                                        if (fileName.text == null || !fileName.text.endsWith(string.substringAfterLast('.'))) {
                                            if (fileName.text == null) {
                                                label.text = "Must enter a file name!"
                                            } else if (!fileName.text.endsWith(string.substringAfterLast('.'))) {
                                                label.text = "File extension must be \"${string.substringAfterLast('.')}\"!"
                                            } else {
                                                label.text = "Must enter a file path!"
                                            }
                                            fileName.text = label.text
                                        } else {
                                            result = value!! + "\\" + fileName.text
                                            whenFinished(this)
                                        }
                                    } else {
                                        if (fileName.text == null) {
                                            fileName.text = "Must enter a file path!"
                                        } else {
                                            result = value!! + "\\" + fileName.text
                                            whenFinished(this)
                                        }
                                    }
                                }
                            }
                        }
                        spacing = 10.0
                        addClass(Styles.defaultBackground)
                        alignment = Pos.CENTER
                    }
                    spacing = 10.0
                    addClass(Styles.defaultBackground)
                    alignment = Pos.CENTER
                    paddingBottom = 30.0
                }
            }
        }
    }

    private var numberOfFiles = 0

    private fun recursivelyUpdateItem(item: TreeItem<String>): Boolean {
        numberOfFiles++
        if (currentFile.isHidden) return false
        if (currentFile.name.startsWith('.')) return false
        val children = currentFile.listFiles()
        if (children != null) {
            for (child in children) {
                currentFile = child
                val newItem = TreeItem(child.name)
                if (recursivelyUpdateItem(newItem)) {
                    item += newItem
                }
            }
            return true
        }
        return false
    }

    fun indexFiles() {
//        replaceWith(LoadingView(), ViewTransition.Fade(0.5.seconds))
        recursivelyUpdateItem(startingItem)
        setViewFromLoading(this)
    }
}

object HowToUseView : View("Bingo > How To Use") {
    override val root = vbox {
        label("How to use") {
            addClass(Styles.titleLabel)
        }
        label("Press \"Create Bingo Game\" to create a game. From there, input the number of days,\n" +
                "the number of winners, the seed, and the number of card. This will generate a game for you.") {
            addClass(Styles.regularLabel)
        }
        label("Once the game is created, you can export by using the \"Export\" option. In addition to \n" +
                "exporting the regular files, this will create 2 text files, where the balls and winners are stored.") {
            addClass(Styles.regularLabel)
        }
        button("< Back") {
            addHoverEffect()
            action {
                currentState = BingoState.MENU
                replaceWith(BingoMenu, ViewTransition.Fade(0.5.seconds))
            }
        }
        spacing = 20.0
        alignment = Pos.CENTER
        addClass(Styles.defaultBackground)
    }
}

object AdView : View("Bingo > Buy ") {
    override val root = vbox {
        button {

        }
    }
}