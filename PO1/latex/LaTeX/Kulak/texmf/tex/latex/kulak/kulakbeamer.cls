% Corporate beamer-template for KU Leuven en Kulak
% Stijn Rebry - september 2012
% Andries Vansweevelt - 2018 - aanpassing aan nieuwe huisstijl KU Leuven
% Tips en hints voor een beter resultaat altijd welkom

% Deze template is gebaseerd op de lay-out van de beamer style beamerthemekuleuven2. Deze is te downloaden op https://www.kuleuven.be/communicatie/marketing/templates/presentatiemateriaal/index.html
% Niet alle opties van de beamerstyle worden in deze classfile geïmplementeerd.

\NeedsTeXFormat{LaTeX2e}
\ProvidesClass{kulakbeamer}[2018/07/16 Beamer class for KU Leuven]
\DeclareOption*{\PassOptionsToClass{\CurrentOption}{beamer}}
\ProcessOptions\relax
\LoadClass{beamer}

%Fonts en encoding
\RequirePackage[utf8]{inputenc}
%\RequirePackage[T1]{fontenc}
\RequirePackage{lmodern}
\RequirePackage{tikz}		
\RequirePackage{ifthen}				
\usetikzlibrary{shapes,shadows,arrows,backgrounds,mindmap,decorations.pathreplacing,positioning}

%%Kleuren
% Definitie oude kleuren
\definecolor{kulblauw}{RGB}{0,64,122}
\colorlet{kulblauw10} {kulblauw!10!white}
\definecolor{kulappelblauwzeegroen}{RGB}{20,116,146}
\colorlet{kulappelblauwzeegroen10} {kulappelblauwzeegroen!10!white}
\definecolor{kuloranje}{RGB}{245,131,87}
\colorlet{kuloranje10} {kuloranje!10!white}
\definecolor{kulgroen}{RGB}{82,189,236}
\colorlet{kulgroen10} {kulgroen!10!white}

%Kleuren nieuwe stijl 2018: hoofdkleuren
\definecolor{KULblauw1}{RGB}{29,141,176} %Hoofdblauw nieuwe huisstijl
\definecolor{KULblauw2}{RGB}{220,231,240} %Secundair blauw: heel lichtblauw
\definecolor{KULblauw3a}{RGB}{82,189,236} %Tertiair blauw (accent): licht
\definecolor{KULblauw3b}{RGB}{30,110,135} %Tertiair blauw (accent): donker
\definecolor{KULblauw3c}{RGB}{12,73,92} %Tertiair blauw (accent): donkerst
\colorlet{KULblauw3a10} {KULblauw3a!10!white}

%Kleuren nieuwe stijl 2018: andere accentkleuren
\definecolor{KULappelblauwzeegroen}{RGB}{135,192,189} 
\definecolor{KULoranje}{RGB}{231,176,55} 
\definecolor{KULdonkergroen}{RGB}{156,166,90} 
\definecolor{KULgeel}{RGB}{228,218,62} 
\definecolor{KULgrijsblauw}{RGB}{184,208,212}
\definecolor{KULbruin}{RGB}{176,159,118}
\definecolor{KULrood}{RGB}{212,119,110}
\definecolor{KULoker}{RGB}{203,182,16}
\definecolor{KULpaars}{RGB}{170,121,130}
\definecolor{KULroze}{RGB}{199,147,174}
\definecolor{KULlichtgroen}{RGB}{212,216,66}
\definecolor{KULroodbruin}{RGB}{186,113,60}

\colorlet{KULoranje10} {KULoranje!10!white}
\colorlet{KULlichtgroen10} {KULlichtgroen!10!white}

%Kleuren uit beamerstyle
\definecolor{KULdark}		{RGB}{47,77,93}			%KUL dark blue uit beamerstyle
\definecolor{gray}			{gray}{.5}				%gray uit beamerstyle
\definecolor{lgray}			{gray}{.95}				%light gray uit beamerstyle


% Opties instellen
\DeclareOptionBeamer{kul}{
  \renewcommand{\Background}{\includegraphics[width=\paperwidth,height=\paperheight]{kuleuvenBackground}}
  \renewcommand{\Titlepage}{\includegraphics[width=\paperwidth,height=\paperheight]{kuleuvenTitlepage}}
  \renewcommand{\Logo}{\includegraphics[height=.8cm]{kuleuvenLogo}}
  }
\DeclareOptionBeamer{kulak}{
  \providecommand{\Background}{\includegraphics[width=\paperwidth,height=\paperheight]{kulakBackground}}
  \providecommand{\Titlepage}{\includegraphics[width=\paperwidth,height=\paperheight]{kulakTitlepage}}
   \providecommand{\Logo}{\includegraphics[height=.8cm]{kulakLogo}}
  }

\ExecuteOptionsBeamer{kulak} %standaardopties
\ProcessOptionsBeamer


\mode<all>


%%		
%%  INNER THEME
%%

% Text colours
\hypersetup{colorlinks,linkcolor=KULblauw1,urlcolor=KULblauw1} %link colors
\urlstyle{sf}					%url addreses are sans-serif font (not typewritter)

\setbeamercolor{title}			{fg=white}				
\setbeamercolor{title page}		{fg=white}
\setbeamercolor{normal text}	{fg = black}			%text color
\setbeamercolor{frametitle}		{fg=KULblauw1}			%frame title color
\setbeamercolor{section in toc}{fg=KULblauw1}			%toc (section) color
\setbeamercolor{section in toc shaded}{fg=KULblauw1}		%toc (shaded section) color
\setbeamercolor{item}			{fg=KULblauw1}			%item color
\setbeamercolor{subitem}		{fg=KULblauw1}			%subitem color
\setbeamercolor{subsubitem}		{fg=KULblauw1}			%subsubitem color
\setbeamercolor{text}			{fg=white,bg=KULblauw1}	%text block color
\setbeamercolor{caption name}	{fg=black}				%figure and table caption color
\setbeamercolor{abstract title}	{fg=red}			%abstract title color

% Blocks, theorem-like environments and colorboxes
\setbeamerfont{block title} {size=\normalsize}				%block title font size
\setbeamercolor{block title}{bg=lgray,fg=KULblauw1}			%block title colour
\setbeamercolor{block body} {bg=lgray,fg=black}				%block body colour
\setbeamertemplate{qed symbol}{\color{KULblauw1} $\square$}	%end of proof symbol

\setbeamercolor{block title alerted}{bg=white!95!red}				%alertbloc title (red)
\setbeamercolor{block body alerted}{bg=white!95!red}				%alertbloc body

\setbeamercolor{block title example}{bg=white!95!KULblauw1,fg=KULblauw1} 	%example block title (blue)
\setbeamercolor{block body example}{bg=white!95!KULblauw1,fg=black}		%example block body

\setbeamercolor{box1}		{fg=white,bg=KULblauw1}		
\setbeamercolor{box2}		{fg=KULblauw1,bg=lgray}			%five different colour schemes for Beamer colour box
\setbeamercolor{box3}		{fg=black,bg=lgray}
\setbeamercolor{box4}		{fg=red,bg=lgray}	
\setbeamercolor{box5}		{fg=white,bg=red}	

\setbeamercolor{button}		{bg= lgray,fg=KULblauw1}			%button frame

%math font
\usefonttheme[onlymath]{serif}

%outline and margins
\setbeamertemplate{sections/subsections in toc}[circle]			%toc symbols
\setbeamersize{text margin left=21pt,text margin right=21pt} 	%text margins

%itemize list
\setbeamertemplate{itemize item}[triangle] 			%item symbol (triangle)
\setbeamertemplate{itemize subitem}[circle]			%subitem symbol (cirlce)
\renewcommand\textbullet{\ensuremath{\bullet}}		%correction for warning for subitem circle bullect
\setbeamertemplate{itemize subsubitem}{-}			%subsubitem symbol (short hypthen)

%enumerate list
\setbeamertemplate{enumerate item}{\small  \insertenumlabel  \enspace }		%enumerate symbol
\setbeamertemplate{enumerate subitem}{\scriptsize \raisebox{.085em} \insertsubenumlabel } 	%subenumerate symbol
\setbeamertemplate{enumerate subsubitem}{\tiny \raisebox{.18em} \insertsubsubenumlabel } 	%subenumerate symbol


%%		
%%  OUTER THEME
%%
\usebackgroundtemplate{\Background}
\newenvironment{titleframe}
{\bgroup\usebackgroundtemplate{\Titlepage}\begin{frame}[plain]}
{\end{frame}\egroup\addtocounter{framenumber}{-1}}

\newenvironment{outlineframe}[1][]
{\bgroup\usebackgroundtemplate{\Background}\begin{frame}{#1}}
{\end{frame}\egroup}

\setbeamertemplate{navigation symbols}{} %remove the navigation symbols

%head frametitle	
\setbeamertemplate{frametitle}
{
	\vspace{18pt}
	{\large\textbf{\thesection ~ ~ \insertframetitle}} 
}

\defbeamertemplate*{title page}{customized}[1][]
{
	\hspace*{.006\paperheight}
	\begin{minipage}[t]{.66\paperwidth}
		\vspace*{0.277\paperheight}
		{\fontsize{18}{19}\bfseries{\inserttitle}}~\\[12pt]
		{\fontsize{11}{12}\bfseries{\insertsubtitle}}~\\[26pt]
		{\fontsize{10}{11}{\insertauthor}}~\\[2pt]
		{\fontsize{10}{11}{\insertinstitute}}~\\[2pt]
		{\fontsize{10}{11}{\insertdate}}~
	\end{minipage}
}

\defbeamertemplate*{footline}{body}
{\hypersetup{linkcolor=white}
\hspace{.085\paperheight}
\raisebox{8.3pt}{\color{white}\scriptsize\insertframenumber\hspace{11pt} \insertshorttitle}
\hypersetup{colorlinks,linkcolor=KULblauw1,urlcolor=KULblauw1}

}	

\beamertemplatenavigationsymbolsempty
