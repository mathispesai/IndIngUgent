% Corporate poster-template for KU Leuven en Kulak
% Stijn Rebry - september 2015
% Andries Vansweevelt - 2018 - aanpassing aan nieuwe huisstijl KU Leuven
% Tips en hints voor een beter resultaat altijd welkom
\NeedsTeXFormat{LaTeX2e}
\ProvidesClass{kulakposter}[2016/10/17 Poster class for KU Leuven]
\DeclareOption{photo}{
   \providecommand{\thephoto}{\usebox{\photobox}}
   \providecommand{\theemailaddress}{\usebox{\emailaddressbox}}
   \providecommand{\photohere}{}
   }
\DeclareOption{nophoto}{
   \renewcommand{\thephoto}{}
   \renewcommand{\theemailaddress}{}
   \renewcommand{\photohere}{\begin{tabular}{@{}r@{}}
   		\usebox{\photobox}
   \end{tabular}}
   }
\DeclareOption{kulak}{
   \providecommand{\Logo}{kulakLogo.pdf}
   }
\DeclareOption{kul}{
   \renewcommand{\Logo}{kuleuvenLogo.pdf}
   }
\DeclareOption{nobackground}{
   \providecommand{\background}{\definecolor{background}{RGB}{255,255,255}} %Achtergrond wit
}
\DeclareOption{background}
{
   \renewcommand{\background}{\definecolor{background}{RGB}{229,237,244}} %Achtergrond als op ppt-sjablonen
}
\DeclareOption*{\PassOptionsToClass{\CurrentOption}{sciposter}}
\ExecuteOptions{photo,portrait,kulak,nobackground} %standaardopties
\ProcessOptions\relax
\LoadClass{sciposter}
\newlength{\eenheid}
\newcommand{\thegroup}{Wetenschappen}
\newcommand{\group}[1]{\renewcommand{\thegroup}{#1}}
%\newcommand{\beeld}{\includegraphics[height=4\eenheid]{WET}} %Met kubus rechtsboven
\newcommand{\beeld}{}
\newcommand{\Copyright}{Rubik's Cube\copyright\ used by permission of Seven Towns Ltd. www.rubiks.com}
%%%2014 \setlength{\eenheid}{.012615643\paperheight}
\setlength{\eenheid}{.01\paperheight}
\setmargins[0\eenheid]
\addtolength{\topmargin}{12\eenheid} % 84 - 72 - 2 + 1
\addtolength{\oddsidemargin}{2\eenheid}
\addtolength{\textwidth}{-4\eenheid}
\addtolength{\textheight}{-13\eenheid} 
\RequirePackage{times}
\RequirePackage{titling}
\RequirePackage{multicol}
\RequirePackage{graphicx}
	\definecolor{kulblauw}{RGB}{0,64,122} %Oude kleur
	\definecolor{kulappelblauwzeegroen}{RGB}{20,116,146} %Oude kleur
	\definecolor{kuloranje}{RGB}{245,131,87} %Oude kleur
	\definecolor{kulgroen}{RGB}{82,189,236} %Oude kleur
	\definecolor{kulwetgroen}{rgb}{0.635,0.784,0.227} %Oude kleur
	\definecolor{KULblauw1}{RGB}{29,141,176} %Hoofdblauw nieuwe huisstijl
	\definecolor{KULblauw2}{RGB}{220,231,240} %Secundair blauw: heel lichtblauw
	\definecolor{KULblauw3a}{RGB}{82,189,236} %Tertiair blauw (accent): licht
	\definecolor{KULblauw3b}{RGB}{30,110,135} %Tertiair blauw (accent): donker
	\definecolor{KULblauw3c}{RGB}{12,73,92} %Tertiair blauw (accent): donkerst
	\definecolor{BoxCol}{RGB}{29,141,176} %KULblauw3b
	\background
	\definecolor{SectionCol}{rgb}{1,1,1}
	

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
\RequirePackage{tikz}
	\usetikzlibrary{arrows} 
	\usetikzlibrary{decorations.pathmorphing}
	\usetikzlibrary{backgrounds}
	\usetikzlibrary{fit}
	\usetikzlibrary{calc}
	\usetikzlibrary{shadows}

\RequirePackage{fancyhdr}
    \fancyhead{\begin{tikzpicture}[remember picture,overlay]
             \node at ($(current  page.south  west)  + (-\hoffset,  -\voffset)$)
         {
         \begin{tikzpicture}[remember picture, overlay,inner sep=0mm]
             \fill[KULblauw1] (0,0) rectangle (\paperwidth,95\eenheid);
             \fill[background]  (\eenheid,\eenheid) rectangle ($ (\paperwidth,88.8\eenheid) + (-\eenheid,0) $); %($ (,) + (,) $)
             \node[anchor=north west] (logo) at (2.5\eenheid,98\eenheid) {
                  \includegraphics[height=6.25\eenheid]{\Logo}};
             \node[anchor=west] (photo) at (logo.east) {\thephoto};
             \node[anchor=north east] (kubus) at ($ (\paperwidth,94.4\eenheid) + (-1\eenheid,0) $)   {};
             %\node[rotate=-90,anchor=west] at ($ (\paperwidth,94.4\eenheid) + (-.5\eenheid,0) $) {\tiny \textcolor{white}{\Copyright}};
             \node[anchor= west] at (2.5\eenheid,90\eenheid) {\resizebox{!}{1.5\eenheid}{\textcolor{white}{\textbf{\thegroup}}}}; 
             \path (logo.east) -- (kubus.west) node [midway,inner sep=0pt] (a) {};
             \path (0,66\eenheid) -- (\paperwidth,66\eenheid) node(b) {};
             \path (a) -- (a |- b)node[at end] (c) {};
	      \end{tikzpicture}
	      };
	\end{tikzpicture}}
	\fancyfoot{}
	\setlength{\headheight}{0pt}
	\renewcommand{\headrulewidth}{0pt}
	\renewcommand{\footrulewidth}{0pt}
	\pagestyle{fancy}
\newsavebox{\photobox}
\savebox{\photobox}{\hspace*{\eenheid}}
\newcommand{\photo}[1]{\savebox{\photobox}{\usebox{\photobox}\includegraphics[height=6.25\eenheid]{#1}\hspace*{\eenheid}}}
\newcommand{\emailaddress}[1]{\newsavebox{\emailaddressbox}\savebox{\emailaddressbox}{\texttt{#1}}}
\AtBeginDocument{\thispagestyle{fancy}}
\renewcommand{\maketitle}{
\thispagestyle{fancy}
\begin{center}
\textcolor{KULblauw1}{\huge\bf\thetitle}\\[.3\eenheid]
{\LARGE\theauthor}\\[.3\eenheid]
{\Large\theinstitute}\\[.5\eenheid]
\end{center}
}
\newcommand{\maketitlebar}{
	\fancyfoot{
\begin{tikzpicture}[remember picture,overlay]
\node[anchor=south,inner sep=0pt, node distance=0pt] (titeldink) at (c)
{ \resizebox{!}{4\eenheid}{
\begin{tabular}{@{}c@{}}
\textcolor{white}{\huge\bf\thetitle}\\[.3\eenheid]
{\textcolor{white}{\LARGE\theauthor}}\\[.3\eenheid]
{\textcolor{white}{\Large\theinstitute}}\\[.5\eenheid]
\end{tabular}}};
\end{tikzpicture}
}}